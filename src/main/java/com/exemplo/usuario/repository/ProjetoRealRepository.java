package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.projeto.ProjetoReal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRealRepository extends JpaRepository<ProjetoReal, Long> {
}
