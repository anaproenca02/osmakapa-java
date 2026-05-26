package com.exemplo.usuario.domain.projeto;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participacoes_projeto")
public class ParticipacaoProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id")
    private ProjetoReal projeto;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected ParticipacaoProjeto() {
    }

    public ParticipacaoProjeto(Usuario usuario, ProjetoReal projeto) {
        this.usuario = usuario;
        this.projeto = projeto;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ProjetoReal getProjeto() {
        return projeto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
