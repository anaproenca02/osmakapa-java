package com.exemplo.usuario.dto.request;

import com.exemplo.usuario.domain.beneficio.TipoBeneficio;
import jakarta.validation.constraints.NotNull;

public class ResgateBeneficioRequestDTO {

    @NotNull(message = "usuarioId e obrigatorio")
    private Long usuarioId;

    @NotNull(message = "tipo e obrigatorio")
    private TipoBeneficio tipo;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoBeneficio getTipo() {
        return tipo;
    }

    public void setTipo(TipoBeneficio tipo) {
        this.tipo = tipo;
    }
}
