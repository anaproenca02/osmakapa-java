package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.request.CursoRequestDTO;
import com.exemplo.usuario.dto.response.CursoResponseDTO;
import com.exemplo.usuario.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Camada: CONTROLLER.
// Controller conversa com HTTP.
// Ela recebe requisicoes, chama a camada de service e devolve JSON.
@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Cursos")
public class CursoRestController {

    private final CursoService service;

    // Injeção de dependência por construtor.
    // O Spring Container cria o CursoService e o entrega aqui.
    // A controller nao usa new CursoService(...).
    public CursoRestController(CursoService service) {
        this.service = service;
    }

    // @GetMapping e tipico/exclusivo da camada controller.
    // Mapeia HTTP GET para listar recursos.
    @GetMapping
    @Operation(summary = "Listar cursos")
    public List<CursoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por id")
    public CursoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // @PostMapping e tipico/exclusivo da camada controller.
    // Mapeia HTTP POST para criacao de recurso.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar curso")
    public CursoResponseDTO criar(@Valid @RequestBody CursoRequestDTO dto) {
        // @RequestBody converte JSON em objeto Java.
        // @Valid executa as validacoes do DTO antes de chegar na regra de negocio.
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso")
    public CursoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir curso")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
