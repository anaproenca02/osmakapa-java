package com.exemplo.usuario.dto.request;

import jakarta.validation.constraints.NotNull;

public class ParticipacaoProjetoRequestDTO {

    @NotNull(message = "usuarioId e obrigatorio")
    private Long usuarioId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
