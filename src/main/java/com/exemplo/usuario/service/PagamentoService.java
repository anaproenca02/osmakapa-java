package com.exemplo.usuario.service;



import com.exemplo.usuario.domain.assinatura.Assinatura;
import com.exemplo.usuario.domain.pagamento.PagamentoAssinatura;
import com.exemplo.usuario.domain.usuario.Usuario;
import com.exemplo.usuario.dto.request.PagamentoRequestDTO;
import com.exemplo.usuario.dto.response.PagamentoResponseDTO;
import com.exemplo.usuario.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoAssinaturaRepository pagamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AssinaturaRepository assinaturaRepository;

    public PagamentoService(PagamentoAssinaturaRepository pagamentoRepository,
                            UsuarioRepository usuarioRepository,
                            AssinaturaRepository assinaturaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    public List<PagamentoResponseDTO> listarPorUsuario(Long usuarioId) {
        return pagamentoRepository.findByUsuarioId(usuarioId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public PagamentoResponseDTO criar(PagamentoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        PagamentoAssinatura pagamento = new PagamentoAssinatura(usuario, dto.getValor(), dto.getMesReferencia());
        return toDTO(pagamentoRepository.save(pagamento));
    }

    @Transactional
    public PagamentoResponseDTO confirmar(Long pagamentoId) {
        PagamentoAssinatura pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento nao encontrado"));
        pagamento.confirmarPagamento();

        Assinatura assinatura = assinaturaRepository.findByUsuarioId(pagamento.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Assinatura nao encontrada"));
        assinatura.ativar();

        return toDTO(pagamentoRepository.save(pagamento));
    }

    @Transactional
    public PagamentoResponseDTO cancelar(Long pagamentoId) {
        PagamentoAssinatura pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento nao encontrado"));
        pagamento.cancelar();
        return toDTO(pagamentoRepository.save(pagamento));
    }

    private PagamentoResponseDTO toDTO(PagamentoAssinatura pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getUsuario().getId(),
                pagamento.getUsuario().getNome(),
                pagamento.getValor(),
                pagamento.getMesReferencia(),
                pagamento.getDataPagamento(),
                pagamento.getStatus().name()
        );
    }
}
