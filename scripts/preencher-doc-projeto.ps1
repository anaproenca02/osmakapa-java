param(
    [string]$InputDoc = "C:\Users\a.proenca\Documents\projosmakapa\osmakapa-java\ProjetoEmGrupoPOOESPm-preenchido.docx",
    [string]$OutputDoc = "C:\Users\a.proenca\Documents\projosmakapa\osmakapa-java\ProjetoEmGrupoPOOESPm-API-Gamification-Osmakapa-FINAL.docx",
    [string]$ProjectRoot = "C:\Users\a.proenca\Documents\projosmakapa\osmakapa-java"
)

$ErrorActionPreference = "Stop"

function Get-NextPlaceholderRange {
    param([object]$Document)

    foreach ($paragraph in $Document.Paragraphs) {
        $text = $paragraph.Range.Text
        if ($text.Contains("<<") -and $text.Contains("aqui")) {
            return $paragraph.Range
        }
    }

    return $null
}

function Add-ImageAfterRange {
    param(
        [object]$Document,
        [object]$Range,
        [string]$ImagePath
    )

    if (-not $ImagePath -or -not (Test-Path -LiteralPath $ImagePath)) {
        return
    }

    $insertRange = $Range.Duplicate
    $insertRange.Collapse(0)
    $insertRange.InsertParagraphAfter()
    $insertRange.Collapse(0)

    $shape = $Document.InlineShapes.AddPicture($ImagePath, $false, $true, $insertRange)
    $shape.LockAspectRatio = $true
    if ($shape.Width -gt 430) {
        $shape.Width = 430
    }
    $afterImage = $shape.Range.Duplicate
    $afterImage.Collapse(0)
    $afterImage.InsertParagraphAfter()
}

function Fill-Placeholder {
    param(
        [object]$Document,
        [string]$Text,
        [string]$ImagePath = ""
    )

    $range = Get-NextPlaceholderRange $Document
    if ($null -eq $range) {
        throw "Nao existe mais placeholder para preencher."
    }

    $range.Text = $Text + "`r"
    $range.Font.Name = "Calibri"
    $range.Font.Size = 11
    $range.ParagraphFormat.SpaceAfter = 8

    Add-ImageAfterRange $Document $range $ImagePath
}

function Add-FinalSection {
    param(
        [object]$Document,
        [string]$Title,
        [string]$Text,
        [string]$ImagePath = ""
    )

    $range = $Document.Content
    $range.Collapse(0)
    $range.InsertParagraphAfter()
    $range.Collapse(0)
    $range.InsertAfter($Title + "`r")
    $range.Font.Name = "Calibri"
    $range.Font.Bold = $true
    $range.Font.Size = 14

    $range.Collapse(0)
    $range.InsertAfter($Text + "`r")
    $range.Font.Bold = $false
    $range.Font.Size = 11

    Add-ImageAfterRange $Document $range $ImagePath
}

$assets = Join-Path $ProjectRoot "doc-assets"
$photos = @{
    Alunos = Join-Path $assets "alunos-grupo.jpg"
    Campus = Join-Path $assets "campus-alunos.jpg"
    Professor = Join-Path $assets "professor-sala.jpg"
    Curadoria = Join-Path $assets "curadoria-academica.jpg"
}

Copy-Item -LiteralPath $InputDoc -Destination $OutputDoc -Force

$word = New-Object -ComObject Word.Application
$word.Visible = $false
$word.DisplayAlerts = 0

try {
    $doc = $word.Documents.Open($OutputDoc)

    $intro = $doc.Content
    $intro.Collapse(1)
    $intro.InsertBefore("Projeto preenchido - API de Gamificacao Osmakapa Academy`rEquipe: preencher nomes completos e RA dos integrantes.`rProjeto: plataforma de cursos EAD com assinatura, gamificacao, forum, projetos reais, moedas e beneficios.`r`r")

    Fill-Placeholder $doc @"
Funcionalidade definida: jornada gamificada de educacao continuada.

A funcionalidade principal implementada foi uma plataforma de cursos EAD por assinatura, chamada no front-end de Osmakapa Academy. O aluno pode se cadastrar, acessar cursos, realizar matricula, concluir cursos com nota final, ganhar creditos para novos cursos, participar do forum, pontuar no ranking mensal, receber bonus e acumular moedas por participacao em projetos reais.

Objetos principais identificados no dominio:
- Usuario/Aluno: representa o estudante cadastrado na plataforma.
- Assinatura: controla plano, assinatura ativa e evolucao para Premium.
- Curso: representa uma trilha de aprendizagem.
- Matricula: liga aluno e curso, controlando status, nota e bonus.
- PagamentoAssinatura: registra cobrancas mensais.
- PostagemForum e ComentarioForum: representam engajamento academico.
- BonusForumMensal: registra premiacao do aluno mais participativo.
- ProjetoReal e ParticipacaoProjeto: representam experiencias reais e geracao de moedas.
- ResgateBeneficio e TipoBeneficio: representam troca de moedas por curso extra, mentoria ou certificado.
"@ $photos.Alunos

    Fill-Placeholder $doc @"
Classes correspondentes no projeto Java:

Pacote domain:
- usuario/Usuario.java
- assinatura/Assinatura.java e PlanoAssinatura.java
- curso/Curso.java
- matricula/Matricula.java e StatusMatricula.java
- pagamento/PagamentoAssinatura.java e StatusPagamento.java
- forum/PostagemForum.java, ComentarioForum.java e BonusForumMensal.java
- projeto/ProjetoReal.java e ParticipacaoProjeto.java
- beneficio/ResgateBeneficio.java e TipoBeneficio.java
- vo/NomeUsuario.java, EmailUsuario.java, SenhaCriptografada.java, TituloCurso.java e DescricaoCurso.java

Essas classes materializam os objetos do estudo de caso usando orientacao a objetos, entidades de dominio e value objects para encapsular regras de validacao de nome, e-mail, senha, titulo e descricao.
"@

    Fill-Placeholder $doc @"
Relacionamento entre as classes:

- Usuario possui uma Assinatura.
- Usuario realiza varias Matriculas.
- Curso possui varias Matriculas.
- Matricula relaciona Usuario e Curso, armazenando status, nota final e indicacao de bonus.
- Usuario realiza PagamentoAssinatura.
- Usuario cria PostagemForum e ComentarioForum.
- PostagemForum possui ComentarioForum.
- ForumService calcula ranking mensal e pode gerar BonusForumMensal para o aluno mais participativo.
- Usuario pode participar de ProjetoReal por meio de ParticipacaoProjeto.
- ParticipacaoProjeto gera moedas para o Usuario.
- Usuario pode usar moedas em ResgateBeneficio.

Esse modelo atende diretamente as regras: concluir curso com media maior ou igual a 7, ganhar creditos, evoluir plano, participar do forum, receber curso bonus e converter moedas em beneficios.
"@

    Fill-Placeholder $doc @"
Projeto Spring Boot com Maven:

O projeto foi criado como aplicacao Spring Boot usando Maven para build e gestao de dependencias. O arquivo pom.xml declara Java 17, Spring Boot 3.5.5 e as dependencias necessarias:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- spring-boot-starter-validation
- h2
- postgresql
- springdoc-openapi-starter-webmvc-ui
- spring-boot-starter-test

Tambem foram configurados perfis para PostgreSQL e H2 em src/main/resources/application-postgres.properties e application-h2.properties.
"@

    Fill-Placeholder $doc @"
Camadas conforme Clean Architecture e separacao de responsabilidades:

- Entity/Domain: classes em src/main/java/com/exemplo/usuario/domain.
- VO: classes em src/main/java/com/exemplo/usuario/domain/vo.
- Repository: interfaces Spring Data JPA em src/main/java/com/exemplo/usuario/repository.
- Service: regras de negocio em src/main/java/com/exemplo/usuario/service.
- Config: seguranca e Swagger em src/main/java/com/exemplo/usuario/config.

A regra de negocio fica concentrada nos services, enquanto controllers tratam apenas a borda HTTP. Os repositories isolam persistencia, e os DTOs protegem o dominio contra exposicao direta.
"@

    Fill-Placeholder $doc @"
DTO e Controller RESTful:

Camada DTO:
- dto/request: UsuarioRequestDTO, CursoRequestDTO, MatriculaRequestDTO, ConcluirMatriculaRequestDTO, PagamentoRequestDTO, PostagemForumRequestDTO, ComentarioForumRequestDTO, ProjetoRealRequestDTO, ParticipacaoProjetoRequestDTO e ResgateBeneficioRequestDTO.
- dto/response: UsuarioResponseDTO, CursoResponseDTO, MatriculaResponseDTO, PagamentoResponseDTO, PostagemForumResponseDTO, ComentarioForumResponseDTO, RankingForumResponseDTO, ProjetoRealResponseDTO, ParticipacaoProjetoResponseDTO e ResgateBeneficioResponseDTO.

Camada Controller:
- UsuarioRestController
- CursoRestController
- MatriculaRestController
- PagamentoRestController
- ForumRestController
- ProjetoRealRestController
- BeneficioRestController
- ApiExceptionHandler

Os controllers expoem endpoints REST com GET, POST, PUT e DELETE, usando @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @RequestBody, @PathVariable e @Valid.
"@

    Fill-Placeholder $doc @"
Swagger/OpenAPI:

O projeto inclui Springdoc OpenAPI por meio da dependencia springdoc-openapi-starter-webmvc-ui. A classe OpenApiConfig configura a documentacao da API, e o arquivo application.properties define:

springdoc.swagger-ui.path=/swagger-ui.html

Com a aplicacao em execucao, a documentacao pode ser acessada em:
- http://localhost:8080/swagger-ui.html
- via container do front/proxy: http://localhost:3000/swagger-ui.html

Isso permite apresentar os endpoints da API em interface navegavel durante a apresentacao.
"@

    Fill-Placeholder $doc @"
Endpoints implementados:

Usuarios:
- GET /api/usuarios
- GET /api/usuarios/{id}
- POST /api/usuarios

Cursos:
- GET /api/cursos
- GET /api/cursos/{id}
- POST /api/cursos
- PUT /api/cursos/{id}
- DELETE /api/cursos/{id}

Matriculas:
- POST /api/matriculas
- PUT /api/matriculas/{id}/concluir
- GET /api/matriculas/usuario/{usuarioId}

Pagamentos:
- POST /api/pagamentos
- PUT /api/pagamentos/{id}/confirmar
- PUT /api/pagamentos/{id}/cancelar
- GET /api/pagamentos/usuario/{usuarioId}

Forum:
- GET /api/forum/postagens
- POST /api/forum/postagens
- POST /api/forum/postagens/{postagemId}/comentarios
- GET /api/forum/ranking
- POST /api/forum/ranking/bonus

Projetos reais:
- GET /api/projetos-reais
- POST /api/projetos-reais
- POST /api/projetos-reais/{projetoId}/participacoes
- GET /api/projetos-reais/participacoes/usuario/{usuarioId}

Beneficios:
- POST /api/beneficios/resgates
- GET /api/beneficios/resgates/usuario/{usuarioId}
"@

    Fill-Placeholder $doc @"
Aplicacao rodando via Spring Boot:

A aplicacao principal e UsuarioApplication.java. Ao executar o backend, a API fica disponivel na porta 8080. A seguranca esta configurada em SecurityConfig.java com HTTP Basic para /api/**, usando usuario admin e senha 123456.

Fluxo demonstravel:
1. Criar aluno em POST /api/usuarios.
2. Criar curso em POST /api/cursos.
3. Matricular aluno em POST /api/matriculas.
4. Concluir matricula em PUT /api/matriculas/{id}/concluir.
5. Criar postagem e comentario no forum.
6. Consultar ranking mensal.
7. Registrar participacao em projeto real.
8. Resgatar beneficio por moedas.

O front-end Osmakapa Academy tambem consome esses endpoints e mostra visao de aluno, professor e curador.
"@ $photos.Campus

    Fill-Placeholder $doc @"
Aplicacao funcionando via Docker:

O projeto possui Dockerfile multi-stage na raiz com dois targets:
- backend: gera a imagem Java da API.
- frontend: gera a imagem Nginx do front-end academico.

O docker-compose.yml sobe:
- postgres: banco PostgreSQL 16.
- pgadmin: painel administrativo do banco.
- app: backend Spring Boot na porta 8080.
- frontend: Nginx na porta 3000, servindo o Osmakapa Academy e encaminhando /api/** para app:8080.

Comando de execucao:
docker compose up --build

Acessos:
- Front-end: http://localhost:3000
- API: http://localhost:8080
- Swagger: http://localhost:3000/swagger-ui.html
- PgAdmin: http://localhost:5050
"@ $photos.Professor

    Add-FinalSection $doc "Bonus - integracao com Front-End e visao full-stack" @"
Foi criado um front-end em frontend-tech com HTML, CSS e JavaScript, servido via Nginx em container Docker. A interface foi redesenhada como Osmakapa Academy, um app de cursos para alunos, professores e curadores.

Visao do aluno:
- jornada academica, cursos, moedas, creditos e participacao.

Visao do professor:
- acompanhamento da turma, matriculas, notas, assinaturas e forum.

Visao do curador:
- catalogo de trilhas, projetos reais, beneficios e comunidade.

O front-end consome a API usando Basic Auth e proxy reverso do Nginx. Essa entrega atende ao bonus de integracao full-stack e tambem permite demonstrar o fluxo de usuario: se matricular, realizar curso EAD, participar do forum/projeto e ganhar beneficio.
"@ $photos.Curadoria

    Add-FinalSection $doc "Apendice - evidencias complementares" @"
Resumo tecnico:
- Backend em Spring Boot 3.5.5 com Java 17, Maven, Spring Web, Spring Data JPA, Validation, Security, H2, PostgreSQL e Springdoc OpenAPI.
- Banco PostgreSQL em Docker Compose, com PgAdmin e healthcheck.
- API protegida por HTTP Basic para /api/** com usuario admin e senha 123456.
- Front-end Osmakapa Academy servido por Nginx em container Docker, integrado ao backend por proxy reverso para /api/**.
- Camadas implementadas: domain/entity/value object, repository, service, DTO request/response, controller REST e configuration.

Arquivos principais:
- pom.xml
- Dockerfile
- docker-compose.yml
- frontend-tech/index.html
- frontend-tech/styles.css
- frontend-tech/app.js
- frontend-tech/nginx.conf
- src/main/java/com/exemplo/usuario/controller
- src/main/java/com/exemplo/usuario/service
- src/main/java/com/exemplo/usuario/domain
- src/main/java/com/exemplo/usuario/repository
- src/main/java/com/exemplo/usuario/dto
"@

    $doc.Save()
    $doc.Close()
}
finally {
    $word.Quit()
    [System.Runtime.InteropServices.Marshal]::ReleaseComObject($word) | Out-Null
}

Write-Host "Documento preenchido em: $OutputDoc"
