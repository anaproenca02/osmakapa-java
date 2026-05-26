package com.exemplo.usuario.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoRequestDTO {

    @NotNull(message = "usuarioId e obrigatorio")
    private Long usuarioId;

    @NotNull(message = "valor e obrigatorio")
    @DecimalMin(value = "0.01", message = "Valor deve ser positivo")
    private BigDecimal valor;

    @NotNull(message = "mesReferencia e obrigatorio")
    private LocalDate mesReferencia;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
    }
}
