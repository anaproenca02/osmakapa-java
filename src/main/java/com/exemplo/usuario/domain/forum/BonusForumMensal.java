package com.exemplo.usuario.domain.forum;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "bonus_forum_mensal",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ano", "mes"})
)
public class BonusForumMensal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int mes;

    @Column(nullable = false)
    private LocalDateTime concedidoEm;

    protected BonusForumMensal() {
    }

    public BonusForumMensal(Usuario usuario, int ano, int mes) {
        this.usuario = usuario;
        this.ano = ano;
        this.mes = mes;
        this.concedidoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public LocalDateTime getConcedidoEm() {
        return concedidoEm;
    }
}
