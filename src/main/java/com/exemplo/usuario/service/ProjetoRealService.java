package com.exemplo.usuario.service;



import com.exemplo.usuario.domain.assinatura.Assinatura;
import com.exemplo.usuario.domain.projeto.ParticipacaoProjeto;
import com.exemplo.usuario.domain.projeto.ProjetoReal;
import com.exemplo.usuario.domain.usuario.Usuario;
import com.exemplo.usuario.dto.request.ParticipacaoProjetoRequestDTO;
import com.exemplo.usuario.dto.request.ProjetoRealRequestDTO;
import com.exemplo.usuario.dto.response.ParticipacaoProjetoResponseDTO;
import com.exemplo.usuario.dto.response.ProjetoRealResponseDTO;
import com.exemplo.usuario.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoRealService {

    private static final int MOEDAS_POR_PARTICIPACAO = 3;

    private final ProjetoRealRepository projetoRepository;
    private final ParticipacaoProjetoRepository participacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AssinaturaRepository assinaturaRepository;

    public ProjetoRealService(ProjetoRealRepository projetoRepository,
                              ParticipacaoProjetoRepository participacaoRepository,
                              UsuarioRepository usuarioRepository,
                              AssinaturaRepository assinaturaRepository) {
        this.projetoRepository = projetoRepository;
        this.participacaoRepository = participacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    public List<ProjetoRealResponseDTO> listarProjetos() {
        return projetoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<ParticipacaoProjetoResponseDTO> listarParticipacoesPorUsuario(Long usuarioId) {
        return participacaoRepository.findByUsuarioId(usuarioId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public ProjetoRealResponseDTO criarProjeto(ProjetoRealRequestDTO dto) {
        ProjetoReal projeto = new ProjetoReal(dto.getNome(), dto.getDescricao());
        return toDTO(projetoRepository.save(projeto));
    }

    @Transactional
    public ParticipacaoProjetoResponseDTO participar(Long projetoId, ParticipacaoProjetoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        ProjetoReal projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto real nao encontrado"));
        Assinatura assinatura = assinaturaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Assinatura nao encontrada"));

        if (!assinatura.isPremium()) {
            throw new RuntimeException("Somente alunos Premium podem participar de projetos reais.");
        }
        if (!projeto.isAtivo()) {
            throw new RuntimeException("Projeto real inativo.");
        }

        ParticipacaoProjeto participacao = new ParticipacaoProjeto(usuario, projeto);
        assinatura.adicionarMoedas(MOEDAS_POR_PARTICIPACAO);
        return toDTO(participacaoRepository.save(participacao));
    }

    private ProjetoRealResponseDTO toDTO(ProjetoReal projeto) {
        return new ProjetoRealResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.isAtivo());
    }

    private ParticipacaoProjetoResponseDTO toDTO(ParticipacaoProjeto participacao) {
        return new ParticipacaoProjetoResponseDTO(
                participacao.getId(),
                participacao.getUsuario().getId(),
                participacao.getUsuario().getNome(),
                participacao.getProjeto().getId(),
                participacao.getProjeto().getNome(),
                participacao.getCriadoEm(),
                MOEDAS_POR_PARTICIPACAO
        );
    }
}
