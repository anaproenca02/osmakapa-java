package com.exemplo.usuario.dto.response;

public class RankingForumResponseDTO {
    private Long usuarioId;
    private String usuarioNome;
    private long postagens;
    private long comentarios;
    private long pontuacao;

    public RankingForumResponseDTO(Long usuarioId, String usuarioNome, long postagens, long comentarios) {
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.postagens = postagens;
        this.comentarios = comentarios;
        this.pontuacao = postagens + comentarios;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public long getPostagens() {
        return postagens;
    }

    public long getComentarios() {
        return comentarios;
    }

    public long getPontuacao() {
        return pontuacao;
    }
}
