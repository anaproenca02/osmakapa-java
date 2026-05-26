package com.exemplo.usuario.controller;


import com.exemplo.usuario.dto.request.ComentarioForumRequestDTO;
import com.exemplo.usuario.dto.request.PostagemForumRequestDTO;
import com.exemplo.usuario.dto.response.ComentarioForumResponseDTO;
import com.exemplo.usuario.dto.response.PostagemForumResponseDTO;
import com.exemplo.usuario.dto.response.RankingForumResponseDTO;
import com.exemplo.usuario.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
@Tag(name = "Forum")
public class ForumRestController {

    private final ForumService service;

    public ForumRestController(ForumService service) {
        this.service = service;
    }

    @GetMapping("/postagens")
    @Operation(summary = "Listar postagens do forum")
    public List<PostagemForumResponseDTO> listarPostagens() {
        return service.listarPostagens();
    }

    @PostMapping("/postagens")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar postagem no forum")
    public PostagemForumResponseDTO criarPostagem(@Valid @RequestBody PostagemForumRequestDTO dto) {
        return service.criarPostagem(dto);
    }

    @PostMapping("/postagens/{postagemId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Comentar em postagem do forum")
    public ComentarioForumResponseDTO comentar(@PathVariable Long postagemId,
                                               @Valid @RequestBody ComentarioForumRequestDTO dto) {
        return service.comentar(postagemId, dto);
    }

    @GetMapping("/ranking")
    @Operation(summary = "Ranking mensal do forum")
    public List<RankingForumResponseDTO> ranking(@RequestParam(required = false) Integer ano,
                                                 @RequestParam(required = false) Integer mes) {
        LocalDate hoje = LocalDate.now();
        return service.rankingMensal(
                ano != null ? ano : hoje.getYear(),
                mes != null ? mes : hoje.getMonthValue()
        );
    }

    @PostMapping("/ranking/bonus")
    @Operation(summary = "Conceder curso bonus ao aluno mais participativo do mes")
    public RankingForumResponseDTO concederBonus(@RequestParam int ano, @RequestParam int mes) {
        return service.concederBonusMensal(ano, mes);
    }
}
