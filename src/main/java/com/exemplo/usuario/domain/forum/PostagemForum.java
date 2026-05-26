package com.exemplo.usuario.domain.forum;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "postagens_forum")
public class PostagemForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 4000)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected PostagemForum() {
    }

    public PostagemForum(Usuario usuario, String titulo, String conteudo) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
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
