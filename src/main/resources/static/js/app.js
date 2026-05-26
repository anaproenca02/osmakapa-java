const AUTH = "Basic " + btoa("admin:123456");

async function run(action) {
    try {
        await action();
    } catch (error) {
        alert(error.message);
    }
}

async function api(url, options = {}) {
    const response = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            "Authorization": AUTH,
            ...(options.headers || {})
        }
    });

    if (response.status === 204) return null;
    const text = await response.text();
    if (!response.ok) throw new Error(text || "Erro na requisicao");
    return text ? JSON.parse(text) : null;
}

function value(id) {
    return document.getElementById(id).value;
}

async function criarUsuario() {
    await api("/api/usuarios", {
        method: "POST",
        body: JSON.stringify({ nome: value("nome"), email: value("email"), senha: value("senha") })
    });
    await listarUsuarios();
}

async function listarUsuarios() {
    const usuarios = await api("/api/usuarios");
    document.getElementById("usuarios").innerHTML = usuarios.map(u =>
        `<li>${u.id} - ${u.nome} | ${u.plano} | ativa: ${u.assinaturaAtiva} | creditos: ${u.creditosCursos} | concluidos: ${u.cursosConcluidosComSucesso} | moedas: ${u.moedas}</li>`
    ).join("");
}

async function criarCurso() {
    await api("/api/cursos", {
        method: "POST",
        body: JSON.stringify({ titulo: value("tituloCurso"), descricao: value("descricaoCurso") })
    });
    await listarCursos();
}

async function listarCursos() {
    const cursos = await api("/api/cursos");
    document.getElementById("cursos").innerHTML = cursos.map(c => `<li>${c.id} - ${c.titulo}</li>`).join("");
}

async function matricular() {
    const result = await api("/api/matriculas", {
        method: "POST",
        body: JSON.stringify({
            usuarioId: Number(value("matUsuarioId")),
            cursoId: Number(value("matCursoId")),
            bonus: value("matBonus") === "true"
        })
    });
    alert(`Matricula criada: ${result.id}`);
}

async function concluirMatricula() {
    const result = await api(`/api/matriculas/${value("matriculaId")}/concluir`, {
        method: "PUT",
        body: JSON.stringify({ notaFinal: Number(value("notaFinal")) })
    });
    alert(`Matricula ${result.id} concluida com status ${result.status}`);
    await listarUsuarios();
}

async function listarMatriculasUsuario() {
    const matriculas = await api(`/api/matriculas/usuario/${value("consultaUsuarioId")}`);
    document.getElementById("matriculas").innerHTML = matriculas.map(m =>
        `<li>${m.id} - ${m.cursoTitulo} | ${m.status} | nota: ${m.notaFinal ?? "-"} | bonus: ${m.bonus}</li>`
    ).join("");
}

async function criarPostagem() {
    await api("/api/forum/postagens", {
        method: "POST",
        body: JSON.stringify({
            usuarioId: Number(value("forumUsuarioId")),
            titulo: value("forumTitulo"),
            conteudo: value("forumConteudo")
        })
    });
    alert("Postagem criada");
}

async function criarComentario() {
    await api(`/api/forum/postagens/${value("postagemId")}/comentarios`, {
        method: "POST",
        body: JSON.stringify({
            usuarioId: Number(value("forumUsuarioId")),
            conteudo: value("comentarioConteudo")
        })
    });
    alert("Comentario criado");
}

async function listarRanking() {
    const ano = value("rankingAno") || new Date().getFullYear();
    const mes = value("rankingMes") || (new Date().getMonth() + 1);
    const ranking = await api(`/api/forum/ranking?ano=${ano}&mes=${mes}`);
    document.getElementById("ranking").innerHTML = ranking.map(r =>
        `<li>${r.usuarioId} - ${r.usuarioNome} | postagens: ${r.postagens} | comentarios: ${r.comentarios} | pontos: ${r.pontuacao}</li>`
    ).join("");
}

async function concederBonusForum() {
    const vencedor = await api(`/api/forum/ranking/bonus?ano=${value("rankingAno")}&mes=${value("rankingMes")}`, {
        method: "POST"
    });
    alert(`Bonus concedido para ${vencedor.usuarioNome}`);
    await listarUsuarios();
}

async function criarProjetoReal() {
    await api("/api/projetos-reais", {
        method: "POST",
        body: JSON.stringify({ nome: value("projetoNome"), descricao: value("projetoDescricao") })
    });
    await listarProjetos();
}

async function listarProjetos() {
    const projetos = await api("/api/projetos-reais");
    document.getElementById("projetos").innerHTML = projetos.map(p =>
        `<li>${p.id} - ${p.nome} | ativo: ${p.ativo}</li>`
    ).join("");
}

async function participarProjeto() {
    const participacao = await api(`/api/projetos-reais/${value("projetoId")}/participacoes`, {
        method: "POST",
        body: JSON.stringify({ usuarioId: Number(value("projetoUsuarioId")) })
    });
    alert(`Participacao ${participacao.id} registrada. Moedas: +${participacao.moedasGeradas}`);
    await listarUsuarios();
}

async function criarPagamento() {
    const pagamento = await api("/api/pagamentos", {
        method: "POST",
        body: JSON.stringify({
            usuarioId: Number(value("pagUsuarioId")),
            valor: Number(value("pagValor")),
            mesReferencia: value("pagMes")
        })
    });
    document.getElementById("pagamentos").innerHTML = `<li>Pagamento ${pagamento.id} criado: ${pagamento.status}</li>`;
}

async function confirmarPagamento() {
    const pagamento = await api(`/api/pagamentos/${value("pagamentoId")}/confirmar`, { method: "PUT" });
    document.getElementById("pagamentos").innerHTML = `<li>Pagamento ${pagamento.id}: ${pagamento.status}</li>`;
    await listarUsuarios();
}

async function resgatarBeneficio() {
    const resgate = await api("/api/beneficios/resgates", {
        method: "POST",
        body: JSON.stringify({
            usuarioId: Number(value("pagUsuarioId")),
            tipo: value("beneficioTipo")
        })
    });
    document.getElementById("pagamentos").innerHTML = `<li>Resgate ${resgate.id}: ${resgate.tipo} (${resgate.custoMoedas} moedas)</li>`;
    await listarUsuarios();
}
