package com.exemplo.usuario.service;



import com.exemplo.usuario.domain.assinatura.Assinatura;
import com.exemplo.usuario.domain.forum.BonusForumMensal;
import com.exemplo.usuario.domain.forum.ComentarioForum;
import com.exemplo.usuario.domain.forum.PostagemForum;
import com.exemplo.usuario.domain.usuario.Usuario;
import com.exemplo.usuario.dto.request.ComentarioForumRequestDTO;
import com.exemplo.usuario.dto.request.PostagemForumRequestDTO;
import com.exemplo.usuario.dto.response.ComentarioForumResponseDTO;
import com.exemplo.usuario.dto.response.PostagemForumResponseDTO;
import com.exemplo.usuario.dto.response.RankingForumResponseDTO;
import com.exemplo.usuario.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ForumService {

    private final PostagemForumRepository postagemRepository;
    private final ComentarioForumRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final AssinaturaRepository assinaturaRepository;
    private final BonusForumMensalRepository bonusRepository;

    public ForumService(PostagemForumRepository postagemRepository,
                        ComentarioForumRepository comentarioRepository,
                        UsuarioRepository usuarioRepository,
                        AssinaturaRepository assinaturaRepository,
                        BonusForumMensalRepository bonusRepository) {
        this.postagemRepository = postagemRepository;
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.assinaturaRepository = assinaturaRepository;
        this.bonusRepository = bonusRepository;
    }

    public List<PostagemForumResponseDTO> listarPostagens() {
        return postagemRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional
    public PostagemForumResponseDTO criarPostagem(PostagemForumRequestDTO dto) {
        Usuario usuario = buscarUsuario(dto.getUsuarioId());
        PostagemForum postagem = new PostagemForum(usuario, dto.getTitulo(), dto.getConteudo());
        return toDTO(postagemRepository.save(postagem));
    }

    @Transactional
    public ComentarioForumResponseDTO comentar(Long postagemId, ComentarioForumRequestDTO dto) {
        Usuario usuario = buscarUsuario(dto.getUsuarioId());
        PostagemForum postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem nao encontrada"));
        ComentarioForum comentario = new ComentarioForum(postagem, usuario, dto.getConteudo());
        return toDTO(comentarioRepository.save(comentario));
    }

    public List<RankingForumResponseDTO> rankingMensal(int ano, int mes) {
        PeriodoMensal periodo = periodoMensal(ano, mes);
        return usuarioRepository.findAll().stream()
                .map(usuario -> calcularParticipacao(usuario, periodo))
                .filter(item -> item.getPontuacao() > 0)
                .sorted(Comparator.comparingLong(RankingForumResponseDTO::getPontuacao).reversed())
                .toList();
    }

    @Transactional
    public RankingForumResponseDTO concederBonusMensal(int ano, int mes) {
        if (bonusRepository.existsByAnoAndMes(ano, mes)) {
            throw new RuntimeException("Curso bonus ja concedido para este mes.");
        }

        RankingForumResponseDTO vencedor = rankingMensal(ano, mes).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nao ha participacao no forum para o mes informado."));

        Assinatura assinatura = assinaturaRepository.findByUsuarioId(vencedor.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Assinatura nao encontrada"));
        assinatura.adicionarCreditos(1);

        Usuario usuario = buscarUsuario(vencedor.getUsuarioId());
        bonusRepository.save(new BonusForumMensal(usuario, ano, mes));
        return vencedor;
    }

    private RankingForumResponseDTO calcularParticipacao(Usuario usuario, PeriodoMensal periodo) {
        long postagens = postagemRepository.countByUsuarioIdAndCriadoEmBetween(
                usuario.getId(), periodo.inicio(), periodo.fim());
        long comentarios = comentarioRepository.countByUsuarioIdAndCriadoEmBetween(
                usuario.getId(), periodo.inicio(), periodo.fim());
        return new RankingForumResponseDTO(usuario.getId(), usuario.getNome(), postagens, comentarios);
    }

    private Usuario buscarUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
    }

    private PeriodoMensal periodoMensal(int ano, int mes) {
        LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
        LocalDateTime inicio = primeiroDia.atStartOfDay();
        LocalDateTime fim = primeiroDia.plusMonths(1).atStartOfDay().minusNanos(1);
        return new PeriodoMensal(inicio, fim);
    }

    private PostagemForumResponseDTO toDTO(PostagemForum postagem) {
        return new PostagemForumResponseDTO(
                postagem.getId(),
                postagem.getUsuario().getId(),
                postagem.getUsuario().getNome(),
                postagem.getTitulo(),
                postagem.getConteudo(),
                postagem.getCriadoEm()
        );
    }

    private ComentarioForumResponseDTO toDTO(ComentarioForum comentario) {
        return new ComentarioForumResponseDTO(
                comentario.getId(),
                comentario.getPostagem().getId(),
                comentario.getUsuario().getId(),
                comentario.getUsuario().getNome(),
                comentario.getConteudo(),
                comentario.getCriadoEm()
        );
    }

    private record PeriodoMensal(LocalDateTime inicio, LocalDateTime fim) {
    }
}
