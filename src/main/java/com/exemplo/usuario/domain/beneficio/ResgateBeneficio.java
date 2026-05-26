package com.exemplo.usuario.domain.beneficio;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resgates_beneficio")
public class ResgateBeneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoBeneficio tipo;

    @Column(nullable = false)
    private int custoMoedas;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected ResgateBeneficio() {
    }

    public ResgateBeneficio(Usuario usuario, TipoBeneficio tipo, int custoMoedas) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.custoMoedas = custoMoedas;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public TipoBeneficio getTipo() {
        return tipo;
    }

    public int getCustoMoedas() {
        return custoMoedas;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
