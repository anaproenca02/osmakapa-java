package com.exemplo.usuario.controller;


import com.exemplo.usuario.dto.request.ParticipacaoProjetoRequestDTO;
import com.exemplo.usuario.dto.request.ProjetoRealRequestDTO;
import com.exemplo.usuario.dto.response.ParticipacaoProjetoResponseDTO;
import com.exemplo.usuario.dto.response.ProjetoRealResponseDTO;
import com.exemplo.usuario.service.ProjetoRealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos-reais")
@Tag(name = "Projetos Reais")
public class ProjetoRealRestController {

    private final ProjetoRealService service;

    public ProjetoRealRestController(ProjetoRealService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar projetos reais")
    public List<ProjetoRealResponseDTO> listarProjetos() {
        return service.listarProjetos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar projeto real")
    public ProjetoRealResponseDTO criarProjeto(@Valid @RequestBody ProjetoRealRequestDTO dto) {
        return service.criarProjeto(dto);
    }

    @PostMapping("/{projetoId}/participacoes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar participacao em projeto real")
    public ParticipacaoProjetoResponseDTO participar(@PathVariable Long projetoId,
                                                     @Valid @RequestBody ParticipacaoProjetoRequestDTO dto) {
        return service.participar(projetoId, dto);
    }

    @GetMapping("/participacoes/usuario/{usuarioId}")
    @Operation(summary = "Listar participacoes em projetos por usuario")
    public List<ParticipacaoProjetoResponseDTO> listarParticipacoes(@PathVariable Long usuarioId) {
        return service.listarParticipacoesPorUsuario(usuarioId);
    }
}
