package com.exemplo.usuario.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private BigDecimal valor;
    private LocalDate mesReferencia;
    private LocalDate dataPagamento;
    private String status;

    public PagamentoResponseDTO(Long id, Long usuarioId, String usuarioNome, BigDecimal valor,
                                LocalDate mesReferencia, LocalDate dataPagamento, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.valor = valor;
        this.mesReferencia = mesReferencia;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public String getStatus() {
        return status;
    }
}
