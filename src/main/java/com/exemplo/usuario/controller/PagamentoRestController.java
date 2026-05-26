package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.request.PagamentoRequestDTO;
import com.exemplo.usuario.dto.response.PagamentoResponseDTO;
import com.exemplo.usuario.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos")
public class PagamentoRestController {

    private final PagamentoService service;

    public PagamentoRestController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar pagamento mensal de assinatura")
    public PagamentoResponseDTO criar(@Valid @RequestBody PagamentoRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar pagamento e ativar assinatura")
    public PagamentoResponseDTO confirmar(@PathVariable Long id) {
        return service.confirmar(id);
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pagamento")
    public PagamentoResponseDTO cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar pagamentos por usuario")
    public List<PagamentoResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }
}
