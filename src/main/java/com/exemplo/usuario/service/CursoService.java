package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.curso.Curso;
import com.exemplo.usuario.dto.request.CursoRequestDTO;
import com.exemplo.usuario.dto.response.CursoResponseDTO;
import com.exemplo.usuario.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Camada: SERVICE.
// Service concentra regras de negocio e orquestracao do caso de uso.
@Service
public class CursoService {

    private final CursoRepository repository;

    // Injeção de dependência por construtor.
    // O bean de CursoRepository e entregue pelo Spring.
    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<CursoResponseDTO> listarTodos() {
        // findAll() vem do JpaRepository.
        // map(this::toDTO) converte entidade em DTO de saida.
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado"));
        return toDTO(curso);
    }

    // @Transactional e tipico da camada de servico.
    // Indica que o metodo deve executar dentro de uma transacao.
    @Transactional
    public CursoResponseDTO criar(CursoRequestDTO dto) {
        // Aqui o service instancia a entidade de dominio.
        Curso curso = new Curso(dto.getTitulo(), dto.getDescricao());

        // save(...) persiste no banco.
        return toDTO(repository.save(curso));
    }

    @Transactional
    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado"));
        curso.alterarTitulo(dto.getTitulo());
        curso.alterarDescricao(dto.getDescricao());
        return toDTO(repository.save(curso));
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Curso nao encontrado");
        }
        repository.deleteById(id);
    }

    // Metodo privado de apoio para mapear entidade -> DTO.
    private CursoResponseDTO toDTO(Curso curso) {
        return new CursoResponseDTO(curso.getId(), curso.getTitulo(), curso.getDescricao());
    }
}
