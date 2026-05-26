package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.request.ResgateBeneficioRequestDTO;
import com.exemplo.usuario.dto.response.ResgateBeneficioResponseDTO;
import com.exemplo.usuario.service.BeneficioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficios")
@Tag(name = "Beneficios")
public class BeneficioRestController {

    private final BeneficioService service;

    public BeneficioRestController(BeneficioService service) {
        this.service = service;
    }

    @PostMapping("/resgates")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Trocar moedas por cursos e beneficios")
    public ResgateBeneficioResponseDTO resgatar(@Valid @RequestBody ResgateBeneficioRequestDTO dto) {
        return service.resgatar(dto);
    }

    @GetMapping("/resgates/usuario/{usuarioId}")
    @Operation(summary = "Listar resgates de beneficios por usuario")
    public List<ResgateBeneficioResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }
}
