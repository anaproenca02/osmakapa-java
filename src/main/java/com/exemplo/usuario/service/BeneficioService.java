package com.exemplo.usuario.service;



import com.exemplo.usuario.domain.assinatura.Assinatura;
import com.exemplo.usuario.domain.beneficio.ResgateBeneficio;
import com.exemplo.usuario.domain.beneficio.TipoBeneficio;
import com.exemplo.usuario.domain.usuario.Usuario;
import com.exemplo.usuario.dto.request.ResgateBeneficioRequestDTO;
import com.exemplo.usuario.dto.response.ResgateBeneficioResponseDTO;
import com.exemplo.usuario.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BeneficioService {

    private static final int LIMITE_RESGATES_MENSAIS = 3;

    private final ResgateBeneficioRepository resgateRepository;
    private final UsuarioRepository usuarioRepository;
    private final AssinaturaRepository assinaturaRepository;

    public BeneficioService(ResgateBeneficioRepository resgateRepository,
                            UsuarioRepository usuarioRepository,
                            AssinaturaRepository assinaturaRepository) {
        this.resgateRepository = resgateRepository;
        this.usuarioRepository = usuarioRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    public List<ResgateBeneficioResponseDTO> listarPorUsuario(Long usuarioId) {
        return resgateRepository.findByUsuarioId(usuarioId).stream().map(this::toDTO).toList();
    }

    @Transactional
    public ResgateBeneficioResponseDTO resgatar(ResgateBeneficioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        Assinatura assinatura = assinaturaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Assinatura nao encontrada"));

        validarLimiteMensal(usuario.getId());

        int custo = custo(dto.getTipo());
        assinatura.consumirMoedas(custo);
        if (TipoBeneficio.CURSO_EXTRA.equals(dto.getTipo())) {
            assinatura.adicionarCreditos(1);
        }

        ResgateBeneficio resgate = new ResgateBeneficio(usuario, dto.getTipo(), custo);
        return toDTO(resgateRepository.save(resgate));
    }

    private void validarLimiteMensal(Long usuarioId) {
        LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
        LocalDateTime inicio = primeiroDia.atStartOfDay();
        LocalDateTime fim = primeiroDia.plusMonths(1).atStartOfDay().minusNanos(1);
        long resgates = resgateRepository.countByUsuarioIdAndCriadoEmBetween(usuarioId, inicio, fim);
        if (resgates >= LIMITE_RESGATES_MENSAIS) {
            throw new RuntimeException("Limite mensal de beneficios atingido.");
        }
    }

    private int custo(TipoBeneficio tipo) {
        return switch (tipo) {
            case CURSO_EXTRA -> 3;
            case MENTORIA -> 5;
            case CERTIFICADO -> 2;
        };
    }

    private ResgateBeneficioResponseDTO toDTO(ResgateBeneficio resgate) {
        return new ResgateBeneficioResponseDTO(
                resgate.getId(),
                resgate.getUsuario().getId(),
                resgate.getUsuario().getNome(),
                resgate.getTipo().name(),
                resgate.getCustoMoedas(),
                resgate.getCriadoEm()
        );
    }
}
