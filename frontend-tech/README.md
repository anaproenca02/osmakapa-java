# Osmakapa Academy

Front-end acadêmico para consumir a API Spring Boot do projeto, com experiência de app de cursos para alunos, professores e curadores.

## Como usar

### Com Docker Compose

Na raiz do projeto:

```powershell
docker compose up --build
```

Acesse:

- Front-end: `http://localhost:3000`
- API direta: `http://localhost:8080`
- Swagger: `http://localhost:3000/swagger-ui.html`
- PgAdmin: `http://localhost:5050`

No container, o front usa a mesma origem por padrão e o Nginx encaminha `/api/**` para o backend `app:8080`.

### Abrindo sem Docker

1. Inicie a API Java na porta `8080`.
2. Abra `frontend-tech/index.html` no navegador.
3. Informe a base da API como `http://localhost:8080`.
4. Confirme as credenciais:
   - Usuário: `admin`
   - Senha: `123456`

## Módulos da tela

- Início com fotos de alunos e apresentação do campus digital.
- Visão do aluno com jornada, progresso, moedas e créditos.
- Visão do professor com acompanhamento de turma, cursos e debates.
- Visão do curador com catálogo, projetos reais, benefícios e comunidade.
- Catálogo de trilhas com cards visuais.
- Alunos em cards com foto, assinatura, créditos, moedas e progresso.
- Matrículas, notas, assinaturas, fórum, projetos reais e recompensas.

## Observação sobre CORS

Se o navegador bloquear requisições ao abrir o HTML diretamente, sirva esta pasta com um servidor estático ou mova os arquivos para `src/main/resources/static`. A API já exige HTTP Basic Auth para `/api/**`.
