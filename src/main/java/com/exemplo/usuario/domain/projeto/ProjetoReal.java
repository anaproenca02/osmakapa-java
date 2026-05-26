package com.exemplo.usuario.domain.projeto;

import jakarta.persistence.*;

@Entity
@Table(name = "projetos_reais")
public class ProjetoReal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 3000)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo;

    protected ProjetoReal() {
    }

    public ProjetoReal(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = true;
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
