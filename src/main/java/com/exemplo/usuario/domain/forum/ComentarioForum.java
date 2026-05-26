package com.exemplo.usuario.domain.forum;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios_forum")
public class ComentarioForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id")
    private PostagemForum postagem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 2000)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected ComentarioForum() {
    }

    public ComentarioForum(PostagemForum postagem, Usuario usuario, String conteudo) {
        this.postagem = postagem;
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public PostagemForum getPostagem() {
        return postagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
