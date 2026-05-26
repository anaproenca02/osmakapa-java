package com.exemplo.usuario.dto.response;

import java.time.LocalDateTime;

public class ResgateBeneficioResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String tipo;
    private int custoMoedas;
    private LocalDateTime criadoEm;

    public ResgateBeneficioResponseDTO(Long id, Long usuarioId, String usuarioNome, String tipo,
                                       int custoMoedas, LocalDateTime criadoEm) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.tipo = tipo;
        this.custoMoedas = custoMoedas;
        this.criadoEm = criadoEm;
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

    public String getTipo() {
        return tipo;
    }

    public int getCustoMoedas() {
        return custoMoedas;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
