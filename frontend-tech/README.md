# Osmakapa Control Hub

Front-end estático e tecnológico para consumir a API Spring Boot do projeto.

## Como usar

1. Inicie a API Java na porta `8080`.
2. Abra `frontend-tech/index.html` no navegador.
3. Confirme a conexão:
   - Base da API: `http://localhost:8080`
   - Usuário: `admin`
   - Senha: `123456`

## Módulos da tela

- Dashboard com indicadores de usuários, cursos, projetos e fórum.
- Cadastro e listagem de usuários.
- Cadastro e listagem de cursos.
- Matrícula, conclusão de matrícula e consulta por usuário.
- Fórum com postagens, comentários e ranking mensal.
- Projetos reais com participação de alunos.
- Pagamentos com confirmação e cancelamento.
- Benefícios por moedas.

## Observação sobre CORS

Se o navegador bloquear requisições ao abrir o HTML diretamente, sirva esta pasta com um servidor estático ou mova os arquivos para `src/main/resources/static`. A API já exige HTTP Basic Auth para `/api/**`.
