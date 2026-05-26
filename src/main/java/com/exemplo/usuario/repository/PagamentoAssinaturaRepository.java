package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.pagamento.PagamentoAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoAssinaturaRepository extends JpaRepository<PagamentoAssinatura, Long> {

    List<PagamentoAssinatura> findByUsuarioId(Long usuarioId);
}
