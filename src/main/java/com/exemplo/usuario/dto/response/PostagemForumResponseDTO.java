package com.exemplo.usuario.dto.response;

import java.time.LocalDateTime;

public class PostagemForumResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String titulo;
    private String conteudo;
    private LocalDateTime criadoEm;

    public PostagemForumResponseDTO(Long id, Long usuarioId, String usuarioNome, String titulo,
                                    String conteudo, LocalDateTime criadoEm) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.titulo = titulo;
        this.conteudo = conteudo;
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

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
