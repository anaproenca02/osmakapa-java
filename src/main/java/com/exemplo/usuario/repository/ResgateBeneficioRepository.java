package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.beneficio.ResgateBeneficio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ResgateBeneficioRepository extends JpaRepository<ResgateBeneficio, Long> {

    long countByUsuarioIdAndCriadoEmBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);

    List<ResgateBeneficio> findByUsuarioId(Long usuarioId);
}
