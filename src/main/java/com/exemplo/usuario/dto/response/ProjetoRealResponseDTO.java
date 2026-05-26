package com.exemplo.usuario.dto.response;

public class ProjetoRealResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private boolean ativo;

    public ProjetoRealResponseDTO(Long id, String nome, String descricao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
