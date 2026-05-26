package com.exemplo.usuario.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioForumRequestDTO {

    @NotNull(message = "usuarioId e obrigatorio")
    private Long usuarioId;

    @NotBlank(message = "Conteudo e obrigatorio")
    private String conteudo;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
