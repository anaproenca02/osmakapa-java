package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.projeto.ParticipacaoProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipacaoProjetoRepository extends JpaRepository<ParticipacaoProjeto, Long> {

    List<ParticipacaoProjeto> findByUsuarioId(Long usuarioId);
}
