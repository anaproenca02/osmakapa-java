package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.forum.PostagemForum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostagemForumRepository extends JpaRepository<PostagemForum, Long> {

    long countByUsuarioIdAndCriadoEmBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);
}
