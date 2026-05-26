package com.exemplo.usuario.dto.response;

import java.time.LocalDateTime;

public class ParticipacaoProjetoResponseDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Long projetoId;
    private String projetoNome;
    private LocalDateTime criadoEm;
    private int moedasGeradas;

    public ParticipacaoProjetoResponseDTO(Long id, Long usuarioId, String usuarioNome, Long projetoId,
                                          String projetoNome, LocalDateTime criadoEm, int moedasGeradas) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.projetoId = projetoId;
        this.projetoNome = projetoNome;
        this.criadoEm = criadoEm;
        this.moedasGeradas = moedasGeradas;
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

    public Long getProjetoId() {
        return projetoId;
    }

    public String getProjetoNome() {
        return projetoNome;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public int getMoedasGeradas() {
        return moedasGeradas;
    }
}
