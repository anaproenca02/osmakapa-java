param(
    [string]$OutputPptx = "C:\Users\a.proenca\Documents\projosmakapa\osmakapa-java\OsMakapa-Academy-Apresentacao-Tecnologica.pptx",
    [string]$ProjectRoot = "C:\Users\a.proenca\Documents\projosmakapa\osmakapa-java"
)

$ErrorActionPreference = "Stop"

$ppLayoutBlank = 12
$ppSaveAsOpenXMLPresentation = 24
$msoFalse = 0
$msoTrue = -1
$shapeRect = 1
$shapeRoundRect = 5
$shapeOval = 9
$shapeLine = 9

$assets = Join-Path $ProjectRoot "doc-assets"
$imgAlunos = Join-Path $assets "alunos-grupo.jpg"
$imgCampus = Join-Path $assets "campus-alunos.jpg"
$imgProfessor = Join-Path $assets "professor-sala.jpg"
$imgCuradoria = Join-Path $assets "curadoria-academica.jpg"

function Rgb($r, $g, $b) {
    return ($r -bor ($g -shl 8) -bor ($b -shl 16))
}

function Add-BlankSlide($Presentation) {
    return $Presentation.Slides.Add($Presentation.Slides.Count + 1, $ppLayoutBlank)
}

function Set-Background($Slide, $Color = $null) {
    if ($null -eq $Color) {
        $Color = Rgb 9 18 34
    }
    $bg = $Slide.Shapes.AddShape($shapeRect, 0, 0, 960, 540)
    $bg.Fill.ForeColor.RGB = $Color
    $bg.Line.Visible = $msoFalse
    $bg.ZOrder(1)
}

function Add-AccentGrid($Slide) {
    for ($x = 40; $x -lt 960; $x += 80) {
        $line = $Slide.Shapes.AddLine($x, 0, $x, 540)
        $line.Line.ForeColor.RGB = Rgb 22 42 72
        $line.Line.Transparency = 0.45
        $line.Line.Weight = 0.5
    }
    for ($y = 60; $y -lt 540; $y += 80) {
        $line = $Slide.Shapes.AddLine(0, $y, 960, $y)
        $line.Line.ForeColor.RGB = Rgb 22 42 72
        $line.Line.Transparency = 0.45
        $line.Line.Weight = 0.5
    }
}

function Add-Title($Slide, $Text, $X = 54, $Y = 44, $W = 820, $H = 70, $Size = 34) {
    $box = $Slide.Shapes.AddTextbox(1, $X, $Y, $W, $H)
    $box.TextFrame.TextRange.Text = $Text
    $box.TextFrame.TextRange.Font.Name = "Aptos Display"
    $box.TextFrame.TextRange.Font.Size = $Size
    $box.TextFrame.TextRange.Font.Bold = $msoTrue
    $box.TextFrame.TextRange.Font.Color.RGB = Rgb 245 249 255
    return $box
}

function Add-Subtitle($Slide, $Text, $X = 56, $Y = 112, $W = 760, $H = 50, $Size = 15) {
    $box = $Slide.Shapes.AddTextbox(1, $X, $Y, $W, $H)
    $box.TextFrame.TextRange.Text = $Text
    $box.TextFrame.TextRange.Font.Name = "Aptos"
    $box.TextFrame.TextRange.Font.Size = $Size
    $box.TextFrame.TextRange.Font.Color.RGB = Rgb 176 192 214
    return $box
}

function Add-Eyebrow($Slide, $Text, $X = 56, $Y = 24, $W = 520, $H = 24) {
    $box = $Slide.Shapes.AddTextbox(1, $X, $Y, $W, $H)
    $box.TextFrame.TextRange.Text = $Text
    $box.TextFrame.TextRange.Font.Name = "Aptos"
    $box.TextFrame.TextRange.Font.Size = 10
    $box.TextFrame.TextRange.Font.Bold = $msoTrue
    $box.TextFrame.TextRange.Font.Color.RGB = Rgb 80 220 255
    return $box
}

function Add-Card($Slide, $X, $Y, $W, $H, $Title, $Body, $Accent = "cyan") {
    $card = $Slide.Shapes.AddShape($shapeRoundRect, $X, $Y, $W, $H)
    $card.Fill.ForeColor.RGB = Rgb 18 33 58
    $card.Fill.Transparency = 0.05
    $card.Line.ForeColor.RGB = Rgb 55 84 130
    $card.Line.Weight = 1

    $accentColor = Rgb 77 218 255
    if ($Accent -eq "green") { $accentColor = Rgb 85 230 160 }
    if ($Accent -eq "gold") { $accentColor = Rgb 232 195 107 }
    if ($Accent -eq "rose") { $accentColor = Rgb 255 96 150 }

    $bar = $Slide.Shapes.AddShape($shapeRect, $X, $Y, 6, $H)
    $bar.Fill.ForeColor.RGB = $accentColor
    $bar.Line.Visible = $msoFalse

    $t = $Slide.Shapes.AddTextbox(1, $X + 18, $Y + 14, $W - 28, 30)
    $t.TextFrame.TextRange.Text = $Title
    $t.TextFrame.TextRange.Font.Name = "Aptos Display"
    $t.TextFrame.TextRange.Font.Size = 15
    $t.TextFrame.TextRange.Font.Bold = $msoTrue
    $t.TextFrame.TextRange.Font.Color.RGB = Rgb 245 249 255

    $b = $Slide.Shapes.AddTextbox(1, $X + 18, $Y + 48, $W - 28, $H - 56)
    $b.TextFrame.TextRange.Text = $Body
    $b.TextFrame.TextRange.Font.Name = "Aptos"
    $b.TextFrame.TextRange.Font.Size = 11
    $b.TextFrame.TextRange.Font.Color.RGB = Rgb 185 199 220
}

function Add-BulletBox($Slide, $X, $Y, $W, $H, $Lines, $Size = 14) {
    $text = ($Lines | ForEach-Object { "• " + $_ }) -join "`r"
    $box = $Slide.Shapes.AddTextbox(1, $X, $Y, $W, $H)
    $box.TextFrame.TextRange.Text = $text
    $box.TextFrame.TextRange.Font.Name = "Aptos"
    $box.TextFrame.TextRange.Font.Size = $Size
    $box.TextFrame.TextRange.Font.Color.RGB = Rgb 220 231 245
    $box.TextFrame.MarginLeft = 4
    return $box
}

function Add-Pill($Slide, $X, $Y, $W, $Text, $Color) {
    $pill = $Slide.Shapes.AddShape($shapeRoundRect, $X, $Y, $W, 30)
    $pill.Fill.ForeColor.RGB = $Color
    $pill.Line.Visible = $msoFalse
    $txt = $Slide.Shapes.AddTextbox(1, $X, $Y + 5, $W, 20)
    $txt.TextFrame.TextRange.Text = $Text
    $txt.TextFrame.TextRange.Font.Name = "Aptos"
    $txt.TextFrame.TextRange.Font.Size = 10
    $txt.TextFrame.TextRange.Font.Bold = $msoTrue
    $txt.TextFrame.TextRange.Font.Color.RGB = Rgb 8 18 34
    $txt.TextFrame.TextRange.ParagraphFormat.Alignment = 2
}

function Add-Photo($Slide, $Path, $X, $Y, $W, $H) {
    if (Test-Path -LiteralPath $Path) {
        $pic = $Slide.Shapes.AddPicture($Path, $msoFalse, $msoTrue, $X, $Y, $W, $H)
        $pic.Line.ForeColor.RGB = Rgb 55 84 130
        $pic.Line.Weight = 1
        return $pic
    }
}

function Add-Footer($Slide, $IndexText = "Osmakapa Academy | API Spring Boot") {
    $line = $Slide.Shapes.AddLine(56, 505, 904, 505)
    $line.Line.ForeColor.RGB = Rgb 55 84 130
    $line.Line.Transparency = 0.2
    $txt = $Slide.Shapes.AddTextbox(1, 56, 512, 600, 18)
    $txt.TextFrame.TextRange.Text = $IndexText
    $txt.TextFrame.TextRange.Font.Name = "Aptos"
    $txt.TextFrame.TextRange.Font.Size = 9
    $txt.TextFrame.TextRange.Font.Color.RGB = Rgb 128 148 176
}

if (Test-Path -LiteralPath $OutputPptx) {
    Remove-Item -LiteralPath $OutputPptx -Force
}

$ppt = New-Object -ComObject PowerPoint.Application
$ppt.Visible = $msoTrue
$presentation = $ppt.Presentations.Add()
$presentation.PageSetup.SlideWidth = 960
$presentation.PageSetup.SlideHeight = 540

try {
    # 1
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-AccentGrid $s
    Add-Photo $s $imgCampus 520 0 440 540
    $overlay = $s.Shapes.AddShape($shapeRect, 470, 0, 490, 540)
    $overlay.Fill.ForeColor.RGB = Rgb 9 18 34
    $overlay.Fill.Transparency = 0.28
    $overlay.Line.Visible = $msoFalse
    Add-Eyebrow $s "PROJETO EM GRUPO | ENGENHARIA DE SOFTWARE"
    Add-Title $s "Osmakapa Academy" 56 86 510 70 42
    Add-Subtitle $s "API Spring Boot para cursos EAD com gamificacao, forum, projetos reais, moedas e beneficios." 58 158 470 70 17
    Add-Pill $s 58 250 125 "Spring Boot" (Rgb 77 218 255)
    Add-Pill $s 192 250 95 "Docker" (Rgb 85 230 160)
    Add-Pill $s 296 250 130 "PostgreSQL" (Rgb 232 195 107)
    Add-Pill $s 435 250 95 "Front-end" (Rgb 255 96 150)
    Add-Subtitle $s "Ana Luiza Proenca Galvao | Gustavo Gomes | Kaique Lira" 58 455 480 30 13

    # 2
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "01 | ESTUDO DE CASO"
    Add-Title $s "Desafio: engajamento em educacao continuada"
    Add-Subtitle $s "A plataforma vende cursos EAD por assinatura e precisa manter o aluno ativo por meio de progresso, comunidade e recompensas."
    Add-Card $s 58 190 250 190 "Assinatura" "Aluno paga mensalmente e acessa cursos do plano basico." "cyan"
    Add-Card $s 354 190 250 190 "Progresso" "Ao concluir curso com media acima de 7, ganha creditos para novos cursos." "green"
    Add-Card $s 650 190 250 190 "Gamificacao" "Forum, ranking, projetos reais, moedas e beneficios aumentam engajamento." "gold"
    Add-Footer $s

    # 3
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "02 | SOLUCAO"
    Add-Title $s "Funcionalidade principal"
    Add-Subtitle $s "Jornada gamificada de educacao continuada, implementada como API REST e consumida pelo front-end Osmakapa Academy."
    Add-Photo $s $imgAlunos 610 110 280 330
    Add-BulletBox $s 64 175 480 230 @(
        "Cadastro de alunos e assinatura ativa",
        "Catalogo de cursos e matriculas",
        "Conclusao com nota final e creditos",
        "Forum, comentarios e ranking mensal",
        "Projetos reais, moedas e resgate de beneficios"
    ) 16
    Add-Footer $s

    # 4
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "03 | VISAO DO PRODUTO"
    Add-Title $s "Tres experiencias no mesmo ecossistema"
    Add-Card $s 58 150 260 230 "Aluno" "Visualiza cursos, progresso, moedas, creditos, forum e beneficios conquistados." "cyan"
    Add-Card $s 350 150 260 230 "Professor" "Acompanha turma, matriculas, notas, participacao e evolucao academica." "green"
    Add-Card $s 642 150 260 230 "Curador" "Organiza catalogo, projetos reais, recompensas e qualidade da jornada." "gold"
    Add-Footer $s

    # 5
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "04 | BACKLOG"
    Add-Title $s "Backlog principal do MVP"
    Add-Card $s 56 130 405 105 "Must Have" "Usuarios, cursos, matriculas, conclusao, progresso, forum, ranking, projetos reais e beneficios." "cyan"
    Add-Card $s 500 130 405 105 "Should Have" "Pagamentos, assinatura, Swagger, Docker Compose e front-end academico integrado." "green"
    Add-Card $s 56 270 405 105 "Could Have" "Dashboard avancado do professor, curadoria, filtros, relatorios e notificacoes." "gold"
    Add-Card $s 500 270 405 105 "Out of Scope" "Gateway de pagamento real, criptomoeda real, app mobile nativo e LLM Gemini." "rose"
    Add-Footer $s

    # 6
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "05 | DOMINIO"
    Add-Title $s "Objetos e classes principais"
    Add-BulletBox $s 60 135 390 290 @(
        "Usuario / Assinatura / PlanoAssinatura",
        "Curso / Matricula / StatusMatricula",
        "PagamentoAssinatura / StatusPagamento",
        "PostagemForum / ComentarioForum / BonusForumMensal",
        "ProjetoReal / ParticipacaoProjeto",
        "ResgateBeneficio / TipoBeneficio",
        "VOs: Nome, Email, Senha, Titulo e Descricao"
    ) 13
    Add-Card $s 520 150 330 210 "Regra central" "Aluno conclui curso com media >= 7, ganha creditos, participa do forum, sobe no ranking, ganha bonus e usa moedas para beneficios." "green"
    Add-Footer $s

    # 7
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "06 | ARQUITETURA"
    Add-Title $s "Camadas do projeto"
    $layers = @(
        @("Controller", "REST, rotas, validacao de entrada"),
        @("DTO", "Request/Response sem expor dominio"),
        @("Service", "Regras de negocio e casos de uso"),
        @("Repository", "Persistencia com Spring Data JPA"),
        @("Domain + VO", "Entidades, objetos de valor e regras")
    )
    $y = 132
    foreach ($layer in $layers) {
        Add-Card $s 90 $y 760 58 $layer[0] $layer[1] "cyan"
        $y += 70
    }
    Add-Footer $s

    # 8
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "07 | API REST"
    Add-Title $s "Endpoints implementados"
    Add-Card $s 54 130 260 150 "Academico" "GET/POST/PUT/DELETE /api/cursos`rPOST /api/matriculas`rPUT /api/matriculas/{id}/concluir" "cyan"
    Add-Card $s 350 130 260 150 "Comunidade" "GET/POST /api/forum/postagens`rPOST /comentarios`rGET /api/forum/ranking" "green"
    Add-Card $s 646 130 260 150 "Gamificacao" "POST /api/projetos-reais`rPOST /participacoes`rPOST /api/beneficios/resgates" "gold"
    Add-Card $s 202 325 260 100 "Usuarios" "GET /api/usuarios`rPOST /api/usuarios" "rose"
    Add-Card $s 498 325 260 100 "Financeiro" "POST /api/pagamentos`rPUT confirmar/cancelar" "cyan"
    Add-Footer $s

    # 9
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "08 | FLUXO DE USO"
    Add-Title $s "Jornada demonstravel na apresentacao"
    $steps = @(
        "1. Criar aluno",
        "2. Criar curso",
        "3. Matricular",
        "4. Concluir com nota",
        "5. Interagir no forum",
        "6. Participar de projeto real",
        "7. Resgatar beneficio"
    )
    $x = 70
    foreach ($step in $steps) {
        Add-Pill $s $x 210 105 $step (Rgb 77 218 255)
        $x += 118
    }
    Add-Card $s 160 310 640 105 "Resultado esperado" "O aluno acumula progresso academico, creditos, moedas, participacao no ranking e beneficios dentro da plataforma." "green"
    Add-Footer $s

    # 10
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "09 | GAMIFICACAO"
    Add-Title $s "Regras de engajamento"
    Add-Photo $s $imgProfessor 620 125 270 315
    Add-BulletBox $s 64 150 490 260 @(
        "Media >= 7 libera creditos para mais cursos",
        "Aluno mais participativo do forum recebe bonus mensal",
        "Projetos reais geram moedas",
        "Moedas podem virar curso extra, mentoria ou certificado",
        "Ao conquistar 12 cursos, aluno evolui para Premium"
    ) 15
    Add-Footer $s

    # 11
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "10 | FRONT-END"
    Add-Title $s "Osmakapa Academy"
    Add-Subtitle $s "Interface academica com fotos, cards, progresso e visoes do aluno, professor e curador."
    Add-Photo $s $imgCuradoria 610 116 280 320
    Add-BulletBox $s 64 170 470 230 @(
        "HTML, CSS e JavaScript",
        "Cards de alunos e cursos",
        "Visao do aluno, professor e curador",
        "Consumo da API com Basic Auth",
        "Nginx com proxy /api/** para o backend"
    ) 15
    Add-Footer $s

    # 12
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "11 | INFRAESTRUTURA"
    Add-Title $s "Docker e ambiente integrado"
    Add-Card $s 62 145 190 150 "frontend" "Nginx`rporta 3000`rproxy /api/**" "cyan"
    Add-Card $s 282 145 190 150 "app" "Spring Boot`rporta 8080`rSwagger" "green"
    Add-Card $s 502 145 190 150 "postgres" "PostgreSQL 16`rporta 5432`rusuario_db" "gold"
    Add-Card $s 722 145 170 150 "pgAdmin" "Painel web`rporta 5050`radmin/admin" "rose"
    Add-Subtitle $s "Comando principal: docker compose up --build" 66 350 740 35 18
    Add-Footer $s

    # 13
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-Eyebrow $s "12 | EVIDENCIAS"
    Add-Title $s "O que mostrar na banca"
    Add-BulletBox $s 70 145 760 260 @(
        "Swagger em http://localhost:8080/swagger-ui.html com endpoints abertos",
        "Front-end em http://localhost:3000 mostrando Osmakapa Academy",
        "Docker Desktop com containers: frontend, app, postgres e pgadmin",
        "PgAdmin em http://localhost:5050 conectado no banco usuario_db",
        "Fluxo completo: aluno -> curso -> matricula -> nota -> forum -> beneficio"
    ) 16
    Add-Footer $s

    # 14
    $s = Add-BlankSlide $presentation
    Set-Background $s
    Add-AccentGrid $s
    Add-Title $s "Conclusao" 58 92 770 70 42
    Add-Subtitle $s "O projeto entrega uma API orientada a objetos, organizada em camadas, com persistencia, documentacao, Docker e front-end integrado." 60 170 720 60 18
    Add-Card $s 75 280 240 100 "API" "Spring Boot + Maven + JPA + Security" "cyan"
    Add-Card $s 360 280 240 100 "Dados" "PostgreSQL + H2 + PgAdmin" "green"
    Add-Card $s 645 280 240 100 "Experiencia" "Osmakapa Academy + Docker" "gold"
    Add-Subtitle $s "Obrigado!" 60 455 300 34 22

    $presentation.SaveAs($OutputPptx, $ppSaveAsOpenXMLPresentation)
}
finally {
    $presentation.Close()
    $ppt.Quit()
    [System.Runtime.InteropServices.Marshal]::ReleaseComObject($ppt) | Out-Null
}

Write-Host "Apresentacao gerada em: $OutputPptx"
