const state = {
    apiBase: localStorage.getItem("ok_api_base") || "http://localhost:8080",
    authUser: localStorage.getItem("ok_auth_user") || "admin",
    authPass: localStorage.getItem("ok_auth_pass") || "123456",
    usuarios: [],
    cursos: [],
    projetos: [],
    postagens: [],
    ranking: []
};

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => [...document.querySelectorAll(selector)];

function authHeader() {
    return "Basic " + btoa(`${state.authUser}:${state.authPass}`);
}

function showToast(message) {
    const toast = $("#toast");
    toast.textContent = message;
    toast.classList.add("show");
    window.clearTimeout(showToast.timer);
    showToast.timer = window.setTimeout(() => toast.classList.remove("show"), 3600);
}

function setConnectionStatus(status, detail) {
    const dot = $("#statusDot");
    dot.className = `status-dot ${status}`;
    $("#connectionLabel").textContent = status === "online" ? "API conectada" : status === "offline" ? "API indisponível" : "Aguardando API";
    $("#connectionDetail").textContent = detail || state.apiBase;
}

async function request(path, options = {}) {
    const response = await fetch(state.apiBase + path, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            "Authorization": authHeader(),
            ...(options.headers || {})
        }
    });

    const text = await response.text();
    const data = text ? tryJson(text) : null;

    if (!response.ok) {
        const message = typeof data === "object" && data !== null
            ? data.message || data.error || JSON.stringify(data)
            : text || `Erro HTTP ${response.status}`;
        throw new Error(message);
    }

    setConnectionStatus("online");
    return data;
}

function tryJson(text) {
    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

function formToObject(form) {
    const data = new FormData(form);
    return Object.fromEntries(data.entries());
}

function asNumber(value) {
    return value === "" || value === null || value === undefined ? null : Number(value);
}

function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function formatDate(value) {
    if (!value) return "-";
    return new Intl.DateTimeFormat("pt-BR").format(new Date(value));
}

function empty(label) {
    return `<div class="empty">${label}</div>`;
}

async function loadUsuarios() {
    state.usuarios = await request("/api/usuarios");
    renderUsuarios();
}

async function loadCursos() {
    state.cursos = await request("/api/cursos");
    renderCursos();
}

async function loadProjetos() {
    state.projetos = await request("/api/projetos-reais");
    renderProjetos();
}

async function loadForum() {
    const now = new Date();
    state.postagens = await request("/api/forum/postagens");
    state.ranking = await request(`/api/forum/ranking?ano=${now.getFullYear()}&mes=${now.getMonth() + 1}`);
    renderForum();
}

async function refreshAll() {
    try {
        await Promise.all([loadUsuarios(), loadCursos(), loadProjetos(), loadForum()]);
        renderMetrics();
        showToast("Dados sincronizados com a API.");
    } catch (error) {
        setConnectionStatus("offline", error.message);
        showToast("Não foi possível sincronizar: " + error.message);
    }
}

function renderMetrics() {
    $("#metricUsuarios").textContent = state.usuarios.length;
    $("#metricCursos").textContent = state.cursos.length;
    $("#metricProjetos").textContent = state.projetos.length;
    $("#metricForum").textContent = state.postagens.length;
}

function renderUsuarios() {
    $("#usuariosTable").innerHTML = state.usuarios.map((user) => `
        <tr>
            <td>#${user.id}</td>
            <td>
                <strong>${escapeHtml(user.nome)}</strong><br>
                <small>${escapeHtml(user.email)}</small>
            </td>
            <td>${escapeHtml(user.plano || "BASICO")}</td>
            <td>${user.creditosCursos ?? 0}</td>
            <td>${user.moedas ?? 0}</td>
            <td><span class="badge ${user.assinaturaAtiva ? "" : "muted"}">${user.assinaturaAtiva ? "Ativa" : "Inativa"}</span></td>
        </tr>
    `).join("");
    renderMetrics();
}

function renderCursos() {
    $("#cursosList").innerHTML = state.cursos.length ? state.cursos.map((curso) => `
        <div class="item">
            <div class="item-header">
                <strong>#${curso.id} ${escapeHtml(curso.titulo)}</strong>
                <span class="badge">Curso</span>
            </div>
            <p>${escapeHtml(curso.descricao || "Sem descrição cadastrada.")}</p>
        </div>
    `).join("") : empty("Nenhum curso cadastrado.");
    renderMetrics();
}

function renderProjetos() {
    $("#projetosList").innerHTML = state.projetos.length ? state.projetos.map((projeto) => `
        <div class="item">
            <div class="item-header">
                <strong>#${projeto.id} ${escapeHtml(projeto.nome)}</strong>
                <span class="badge ${projeto.ativo ? "" : "muted"}">${projeto.ativo ? "Ativo" : "Inativo"}</span>
            </div>
            <p>${escapeHtml(projeto.descricao)}</p>
        </div>
    `).join("") : empty("Nenhum projeto real cadastrado.");
    renderMetrics();
}

function renderForum() {
    $("#postagensList").innerHTML = state.postagens.length ? state.postagens.map((post) => `
        <div class="item">
            <div class="item-header">
                <strong>#${post.id} ${escapeHtml(post.titulo)}</strong>
                <small>${formatDate(post.criadoEm)}</small>
            </div>
            <small>${escapeHtml(post.usuarioNome)} · usuário #${post.usuarioId}</small>
            <p>${escapeHtml(post.conteudo)}</p>
        </div>
    `).join("") : empty("Nenhuma postagem no fórum.");

    $("#rankingList").innerHTML = state.ranking.length ? state.ranking.map((rank, index) => `
        <div class="ranking-row">
            <span class="rank-pos">${index + 1}</span>
            <div>
                <strong>${escapeHtml(rank.usuarioNome)}</strong>
                <small>${rank.postagens} posts · ${rank.comentarios} comentários</small>
            </div>
            <strong>${rank.pontuacao} pts</strong>
        </div>
    `).join("") : empty("Ranking ainda vazio.");
    renderMetrics();
}

function renderMatriculas(items) {
    $("#matriculasList").innerHTML = items.length ? items.map((matricula) => `
        <div class="item">
            <div class="item-header">
                <strong>#${matricula.id} ${escapeHtml(matricula.cursoTitulo)}</strong>
                <span class="badge ${matricula.status === "CONCLUIDA" ? "" : "muted"}">${escapeHtml(matricula.status)}</span>
            </div>
            <small>${escapeHtml(matricula.usuarioNome)} · nota ${matricula.notaFinal ?? "-"} · ${matricula.bonus ? "bônus" : "regular"}</small>
        </div>
    `).join("") : empty("Nenhuma matrícula encontrada para esse usuário.");
}

function renderPagamentos(items) {
    $("#pagamentosList").innerHTML = items.length ? items.map((pagamento) => `
        <div class="item">
            <div class="item-header">
                <strong>#${pagamento.id} R$ ${Number(pagamento.valor).toFixed(2)}</strong>
                <span class="badge ${pagamento.status === "CONFIRMADO" ? "" : "muted"}">${escapeHtml(pagamento.status)}</span>
            </div>
            <small>${escapeHtml(pagamento.usuarioNome)} · ref. ${formatDate(pagamento.mesReferencia)}</small>
            <p>
                <button class="mini-button" data-confirmar-pagamento="${pagamento.id}">Confirmar</button>
                <button class="mini-button" data-cancelar-pagamento="${pagamento.id}">Cancelar</button>
            </p>
        </div>
    `).join("") : empty("Nenhum pagamento encontrado para esse usuário.");
}

function renderBeneficios(items) {
    $("#beneficiosList").innerHTML = items.length ? items.map((beneficio) => `
        <div class="item">
            <div class="item-header">
                <strong>#${beneficio.id} ${escapeHtml(beneficio.tipo)}</strong>
                <span class="badge">${beneficio.custoMoedas} moedas</span>
            </div>
            <small>${escapeHtml(beneficio.usuarioNome)} · ${formatDate(beneficio.criadoEm)}</small>
        </div>
    `).join("") : empty("Nenhum benefício encontrado para esse usuário.");
}

async function submitJson(form, path, payload, successMessage, after = refreshAll) {
    try {
        await request(path, {
            method: "POST",
            body: JSON.stringify(payload)
        });
        form.reset();
        showToast(successMessage);
        await after();
    } catch (error) {
        showToast(error.message);
    }
}

function setupForms() {
    $("#usuarioForm").addEventListener("submit", (event) => {
        event.preventDefault();
        submitJson(event.currentTarget, "/api/usuarios", formToObject(event.currentTarget), "Usuário criado.");
    });

    $("#cursoForm").addEventListener("submit", (event) => {
        event.preventDefault();
        submitJson(event.currentTarget, "/api/cursos", formToObject(event.currentTarget), "Curso publicado.");
    });

    $("#matriculaForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, "/api/matriculas", {
            usuarioId: asNumber(data.usuarioId),
            cursoId: asNumber(data.cursoId),
            bonus: Boolean(data.bonus)
        }, "Matrícula criada.", async () => {
            const items = await request(`/api/matriculas/usuario/${data.usuarioId}`);
            renderMatriculas(items);
        });
    });

    $("#concluirForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        try {
            await request(`/api/matriculas/${data.matriculaId}/concluir`, {
                method: "PUT",
                body: JSON.stringify({ notaFinal: Number(data.notaFinal) })
            });
            form.reset();
            showToast("Matrícula concluída.");
        } catch (error) {
            showToast(error.message);
        }
    });

    $("#postagemForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, "/api/forum/postagens", {
            usuarioId: asNumber(data.usuarioId),
            titulo: data.titulo,
            conteudo: data.conteudo
        }, "Postagem criada.", loadForum);
    });

    $("#comentarioForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, `/api/forum/postagens/${data.postagemId}/comentarios`, {
            usuarioId: asNumber(data.usuarioId),
            conteudo: data.conteudo
        }, "Comentário enviado.", loadForum);
    });

    $("#projetoForm").addEventListener("submit", (event) => {
        event.preventDefault();
        submitJson(event.currentTarget, "/api/projetos-reais", formToObject(event.currentTarget), "Projeto criado.", loadProjetos);
    });

    $("#participacaoForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, `/api/projetos-reais/${data.projetoId}/participacoes`, {
            usuarioId: asNumber(data.usuarioId)
        }, "Participação registrada.", loadProjetos);
    });

    $("#pagamentoForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, "/api/pagamentos", {
            usuarioId: asNumber(data.usuarioId),
            valor: Number(data.valor),
            mesReferencia: data.mesReferencia
        }, "Pagamento criado.", async () => {
            const items = await request(`/api/pagamentos/usuario/${data.usuarioId}`);
            renderPagamentos(items);
        });
    });

    $("#beneficioForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = formToObject(form);
        submitJson(form, "/api/beneficios/resgates", {
            usuarioId: asNumber(data.usuarioId),
            tipo: data.tipo
        }, "Benefício resgatado.", async () => {
            const items = await request(`/api/beneficios/resgates/usuario/${data.usuarioId}`);
            renderBeneficios(items);
            await loadUsuarios();
        });
    });
}

function setupActions() {
    $("#refreshAllBtn").addEventListener("click", refreshAll);

    $("#saveConfigBtn").addEventListener("click", () => {
        state.apiBase = $("#apiBaseInput").value.trim().replace(/\/$/, "");
        state.authUser = $("#authUserInput").value.trim();
        state.authPass = $("#authPassInput").value;
        localStorage.setItem("ok_api_base", state.apiBase);
        localStorage.setItem("ok_auth_user", state.authUser);
        localStorage.setItem("ok_auth_pass", state.authPass);
        setConnectionStatus("", state.apiBase);
        refreshAll();
    });

    $("#buscarMatriculasBtn").addEventListener("click", async () => {
        const usuarioId = $("#matriculasUsuarioId").value;
        if (!usuarioId) return showToast("Informe o ID do usuário.");
        try {
            renderMatriculas(await request(`/api/matriculas/usuario/${usuarioId}`));
        } catch (error) {
            showToast(error.message);
        }
    });

    $("#buscarPagamentosBtn").addEventListener("click", async () => {
        const usuarioId = $("#pagamentosUsuarioId").value;
        if (!usuarioId) return showToast("Informe o ID do usuário.");
        try {
            renderPagamentos(await request(`/api/pagamentos/usuario/${usuarioId}`));
        } catch (error) {
            showToast(error.message);
        }
    });

    $("#buscarBeneficiosBtn").addEventListener("click", async () => {
        const usuarioId = $("#beneficiosUsuarioId").value;
        if (!usuarioId) return showToast("Informe o ID do usuário.");
        try {
            renderBeneficios(await request(`/api/beneficios/resgates/usuario/${usuarioId}`));
        } catch (error) {
            showToast(error.message);
        }
    });

    $("#seedDemoBtn").addEventListener("click", seedDemo);

    document.addEventListener("click", async (event) => {
        const confirmar = event.target.dataset.confirmarPagamento;
        const cancelar = event.target.dataset.cancelarPagamento;
        if (!confirmar && !cancelar) return;
        try {
            await request(`/api/pagamentos/${confirmar || cancelar}/${confirmar ? "confirmar" : "cancelar"}`, { method: "PUT" });
            showToast(confirmar ? "Pagamento confirmado." : "Pagamento cancelado.");
        } catch (error) {
            showToast(error.message);
        }
    });

    $$("[data-refresh]").forEach((button) => {
        button.addEventListener("click", () => {
            const target = button.dataset.refresh;
            const loaders = { usuarios: loadUsuarios, cursos: loadCursos, projetos: loadProjetos, forum: loadForum };
            loaders[target]?.().catch((error) => showToast(error.message));
        });
    });

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (!entry.isIntersecting) return;
            $$(".nav-link").forEach((link) => link.classList.toggle("active", link.getAttribute("href") === `#${entry.target.id}`));
        });
    }, { threshold: 0.25 });

    $$("section[id], article[id]").forEach((section) => observer.observe(section));
}

async function seedDemo() {
    try {
        const stamp = Date.now().toString().slice(-5);
        const usuario = await request("/api/usuarios", {
            method: "POST",
            body: JSON.stringify({
                nome: `Aluno Tech ${stamp}`,
                email: `aluno${stamp}@osmakapa.dev`,
                senha: "123456"
            })
        });
        const curso = await request("/api/cursos", {
            method: "POST",
            body: JSON.stringify({
                titulo: `Arquitetura de APIs ${stamp}`,
                descricao: "Trilha prática para dominar camadas, DTOs, autenticação e integrações REST."
            })
        });
        const projeto = await request("/api/projetos-reais", {
            method: "POST",
            body: JSON.stringify({
                nome: `Sprint real ${stamp}`,
                descricao: "Desafio orientado por entregas para consolidar portfólio técnico."
            })
        });
        await request("/api/matriculas", {
            method: "POST",
            body: JSON.stringify({ usuarioId: usuario.id, cursoId: curso.id, bonus: false })
        });
        await request("/api/forum/postagens", {
            method: "POST",
            body: JSON.stringify({ usuarioId: usuario.id, titulo: "Primeiro checkpoint", conteudo: "Ambiente pronto para evoluir a jornada." })
        });
        await request(`/api/projetos-reais/${projeto.id}/participacoes`, {
            method: "POST",
            body: JSON.stringify({ usuarioId: usuario.id })
        });
        await refreshAll();
        showToast("Demo criada com usuário, curso, matrícula, fórum e projeto.");
    } catch (error) {
        showToast("Demo não criada: " + error.message);
    }
}

function boot() {
    $("#apiBaseInput").value = state.apiBase;
    $("#authUserInput").value = state.authUser;
    $("#authPassInput").value = state.authPass;
    setupForms();
    setupActions();
    refreshAll();
}

boot();
