package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.forum.BonusForumMensal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusForumMensalRepository extends JpaRepository<BonusForumMensal, Long> {

    boolean existsByAnoAndMes(int ano, int mes);
}
