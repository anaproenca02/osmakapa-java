package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.forum.ComentarioForum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ComentarioForumRepository extends JpaRepository<ComentarioForum, Long> {

    long countByUsuarioIdAndCriadoEmBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);
}
