package com.exemplo.usuario.domain.pagamento;

import com.exemplo.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos_assinatura")
public class PagamentoAssinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate mesReferencia;

    @Column
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    protected PagamentoAssinatura() {
    }

    public PagamentoAssinatura(Usuario usuario, BigDecimal valor, LocalDate mesReferencia) {
        this.usuario = usuario;
        this.valor = valor;
        this.mesReferencia = mesReferencia.withDayOfMonth(1);
        this.status = StatusPagamento.PENDENTE;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void confirmarPagamento() {
        this.status = StatusPagamento.PAGO;
        this.dataPagamento = LocalDate.now();
    }

    public void cancelar() {
        this.status = StatusPagamento.CANCELADO;
    }
}
