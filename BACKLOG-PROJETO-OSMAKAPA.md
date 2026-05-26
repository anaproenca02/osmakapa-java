# 10. BACKLOG COMPLETO

## 1. Epics e Bounded Contexts

| ID | Prioridade | Epic | Bounded Context | Owner | Status MVP |
|---|---|---|---|---|---|
| E1 | Must Have | Identidade e Autenticacao | usuario | Toda equipe | Incluido no MVP |
| E2 | Must Have | Catalogo de Cursos | academico | Toda equipe | Incluido no MVP |
| E3 | Must Have | Matricula e Jornada do Aluno | academico | Toda equipe | Incluido no MVP |
| E4 | Must Have | Progresso Academico | academico | Toda equipe | Incluido no MVP |
| E5 | Must Have | Forum e Engajamento | engajamento | Toda equipe | Incluido no MVP |
| E6 | Must Have | Gamificacao e Recompensas | gamificacao | Toda equipe | Incluido no MVP |
| E7 | Must Have | Projetos Reais e Moedas | projeto / gamificacao | Toda equipe | Incluido no MVP |
| E8 | Should Have | Pagamentos e Assinatura | financeiro | Toda equipe | Should Have |
| E9 | Should Have | Front-End Academico | usuario / academico | Toda equipe | Should Have |
| E10 | Should Have | Docker e Ambiente Integrado | infraestrutura | Toda equipe | Should Have |
| E11 | Could Have | Dashboard do Professor | academico / engajamento | Toda equipe | Could Have |
| E12 | Could Have | Curadoria de Trilhas | curadoria / academico | Toda equipe | Could Have |
| E13 | Out of Scope | Gateway de Pagamento Real | financeiro | - | Fora do escopo |
| E14 | Out of Scope | Integracao com Criptomoeda Real | gamificacao / financeiro | - | Fora do escopo |
| E15 | Out of Scope | LLM Gemini na API | inteligencia artificial | - | Fora do escopo |

## 2. User Stories do MVP

| ID | Epic | User Story | Criterios de Aceite | Status |
|---|---|---|---|---|
| US01 | E1 | Como aluno, quero criar meu cadastro para acessar a plataforma de cursos. | Deve permitir informar nome, e-mail e senha; nao deve expor senha na resposta; deve retornar dados do usuario criado. | Implementado |
| US02 | E1 | Como administrador, quero listar usuarios para acompanhar os alunos cadastrados. | Deve retornar id, nome, e-mail, plano, creditos, cursos concluidos, moedas e assinatura ativa. | Implementado |
| US03 | E2 | Como professor ou curador, quero cadastrar cursos para montar o catalogo academico. | Deve permitir titulo e descricao; deve validar titulo obrigatorio; deve retornar o curso criado. | Implementado |
| US04 | E2 | Como aluno, quero consultar cursos disponiveis para escolher uma trilha. | Deve listar todos os cursos cadastrados; deve permitir buscar curso por id. | Implementado |
| US05 | E2 | Como curador, quero atualizar ou excluir cursos para manter o catalogo correto. | Deve permitir PUT por id; deve permitir DELETE por id; deve retornar erro quando curso nao existir. | Implementado |
| US06 | E3 | Como aluno, quero me matricular em um curso para iniciar minha jornada. | Deve receber usuarioId e cursoId; deve criar matricula; deve indicar se e bonus ou regular. | Implementado |
| US07 | E3 | Como professor, quero concluir uma matricula com nota final para registrar desempenho. | Deve aceitar nota de 0 a 10; deve concluir a matricula; se nota atender regra, deve atualizar progresso/creditos. | Implementado |
| US08 | E3 | Como aluno, quero consultar minhas matriculas para acompanhar meus cursos. | Deve listar matriculas por usuarioId, com curso, status, nota e indicacao de bonus. | Implementado |
| US09 | E4 | Como sistema, quero conceder creditos quando o aluno concluir curso com media suficiente. | Ao concluir curso com nota maior ou igual a 7, regra de progresso deve ser aplicada no service. | Implementado |
| US10 | E4 | Como sistema, quero evoluir aluno para Premium ao atingir quantidade definida de cursos. | Ao conquistar 12 cursos com sucesso, plano deve evoluir para Premium conforme regra de dominio. | Implementado |
| US11 | E5 | Como aluno, quero criar postagens no forum para participar da comunidade. | Deve criar postagem com usuarioId, titulo e conteudo; deve listar postagens. | Implementado |
| US12 | E5 | Como aluno, quero comentar postagens para ajudar outros participantes. | Deve permitir comentario em uma postagem existente; deve associar comentario ao usuario. | Implementado |
| US13 | E5 | Como professor ou curador, quero consultar ranking mensal para identificar alunos engajados. | Deve retornar usuario, quantidade de postagens, comentarios e pontuacao mensal. | Implementado |
| US14 | E6 | Como sistema, quero conceder bonus mensal ao aluno mais participativo. | Deve calcular ranking por ano e mes; deve conceder curso bonus ao vencedor. | Implementado |
| US15 | E6 | Como aluno, quero resgatar beneficios usando moedas. | Deve aceitar usuarioId e tipo de beneficio; deve descontar custo em moedas; deve registrar resgate. | Implementado |
| US16 | E7 | Como curador, quero cadastrar projetos reais para alunos Premium participarem. | Deve permitir nome e descricao do projeto; projeto deve ser listado como ativo. | Implementado |
| US17 | E7 | Como aluno, quero participar de projeto real para ganhar moedas e experiencia. | Deve registrar participacao por projetoId e usuarioId; deve gerar moedas para o aluno. | Implementado |
| US18 | E8 | Como administrador, quero criar pagamento mensal de assinatura. | Deve receber usuarioId, valor e mesReferencia; deve registrar pagamento com status inicial. | Implementado |
| US19 | E8 | Como administrador, quero confirmar ou cancelar pagamento para controlar assinatura. | Deve permitir confirmar pagamento; deve permitir cancelar pagamento; deve listar pagamentos por usuario. | Implementado |
| US20 | E9 | Como aluno, quero usar uma interface visual academica para acessar cursos e progresso. | Front deve mostrar fotos, cards de cursos, cards de alunos, progresso, moedas e creditos. | Implementado |
| US21 | E9 | Como professor, quero uma visao para acompanhar turma, matriculas e notas. | Front deve ter area do professor com acoes de matricula, lancamento de nota e acompanhamento. | Implementado |
| US22 | E9 | Como curador, quero uma visao para gerenciar catalogo, projetos e beneficios. | Front deve ter area do curador com cursos, projetos reais, beneficios e comunidade. | Implementado |
| US23 | E10 | Como equipe, quero executar backend, banco, pgAdmin e front em Docker. | docker compose deve subir postgres, pgadmin, app e frontend; front deve acessar API por proxy. | Implementado |

## 3. Priorizacao MoSCoW

| Prioridade | Itens |
|---|---|
| Must Have | Cadastro de usuario, catalogo de cursos, matricula, conclusao de curso, progresso academico, forum, ranking, bonus, beneficios, projetos reais e endpoints REST principais. |
| Should Have | Pagamentos, assinatura, Docker Compose completo, Swagger/OpenAPI e front-end academico integrado. |
| Could Have | Dashboard mais analitico do professor, curadoria avancada, filtros de busca, relatorios e notificacoes. |
| Out of Scope | Gateway de pagamento real, criptomoeda real, app mobile nativo, LLM Gemini e autenticacao JWT completa. |

## 4. Bounded Contexts

| Bounded Context | Responsabilidade | Principais Classes |
|---|---|---|
| usuario | Cadastro, identidade, plano, creditos, moedas e assinatura ativa. | Usuario, NomeUsuario, EmailUsuario, SenhaCriptografada |
| academico | Cursos, matriculas, conclusao, nota e progresso. | Curso, Matricula, StatusMatricula, TituloCurso, DescricaoCurso |
| financeiro | Pagamentos mensais e controle de status financeiro. | PagamentoAssinatura, StatusPagamento, Assinatura, PlanoAssinatura |
| engajamento | Forum, postagens, comentarios e ranking mensal. | PostagemForum, ComentarioForum, BonusForumMensal |
| gamificacao | Bonus, moedas, creditos e resgate de beneficios. | ResgateBeneficio, TipoBeneficio |
| projeto | Projetos reais e participacao dos alunos. | ProjetoReal, ParticipacaoProjeto |
| infraestrutura | Docker, PostgreSQL, PgAdmin, Swagger, Nginx e seguranca. | SecurityConfig, OpenApiConfig, Dockerfile, docker-compose.yml |
| frontend | Experiencia visual do aluno, professor e curador. | frontend-tech/index.html, styles.css, app.js, nginx.conf |

## 5. Roadmap do MVP

| Fase | Entrega | Resultado |
|---|---|---|
| Fase 1 | Modelagem de dominio | Entidades, VOs e relacionamentos principais criados. |
| Fase 2 | API academica | Endpoints de usuarios, cursos e matriculas. |
| Fase 3 | Regras de progresso | Conclusao de curso, nota, creditos e plano Premium. |
| Fase 4 | Engajamento | Forum, comentarios, ranking e bonus mensal. |
| Fase 5 | Gamificacao | Projetos reais, moedas e resgate de beneficios. |
| Fase 6 | Infraestrutura | PostgreSQL, PgAdmin, Docker Compose e Swagger. |
| Fase 7 | Front-end | Osmakapa Academy com visao do aluno, professor e curador. |

