package com.exemplo.usuario.dto.response;

import java.time.LocalDateTime;

public class ComentarioForumResponseDTO {
    private Long id;
    private Long postagemId;
    private Long usuarioId;
    private String usuarioNome;
    private String conteudo;
    private LocalDateTime criadoEm;

    public ComentarioForumResponseDTO(Long id, Long postagemId, Long usuarioId, String usuarioNome,
                                      String conteudo, LocalDateTime criadoEm) {
        this.id = id;
        this.postagemId = postagemId;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.conteudo = conteudo;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public Long getPostagemId() {
        return postagemId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
