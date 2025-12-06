package com.biblioteca.api.application.service;

import com.biblioteca.api.domain.dto.EmprestimoDTO;
import com.biblioteca.api.domain.entity.Emprestimo;
import com.biblioteca.api.domain.entity.Usuario;
import com.biblioteca.api.domain.entity.Livro;
import com.biblioteca.api.domain.repository.EmprestimoRepository;
import com.biblioteca.api.domain.repository.UsuarioRepository;
import com.biblioteca.api.domain.repository.LivroRepository;
import com.biblioteca.api.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public EmprestimoDTO criar(EmprestimoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("Livro não está disponível");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(14)); // 14 dias de prazo
        emprestimo.setStatus("ATIVO");
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);

        // Decrementar quantidade disponível
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);
        return converterParaDTO(emprestimo);
    }

    @Transactional(readOnly = true)
    public EmprestimoDTO obterPorId(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));
        return converterParaDTO(emprestimo);
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDTO> obterTodos() {
        return emprestimoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDTO> buscarPorUsuario(Long usuarioId) {
        return emprestimoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDTO> buscarPorLivro(Long livroId) {
        return emprestimoRepository.findByLivroId(livroId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmprestimoDTO> buscarPorStatus(String status) {
        return emprestimoRepository.findByStatus(status)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmprestimoDTO devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setStatus("DEVOLVIDO");

        // Incrementar quantidade disponível
        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);
        return converterParaDTO(emprestimo);
    }

    @Transactional
    public EmprestimoDTO atualizar(Long id, EmprestimoDTO dto) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        emprestimo.setDataDevolucaoPrevista(dto.getDataDevolucaoPrevista());
        emprestimo.setStatus(dto.getStatus());

        emprestimoRepository.save(emprestimo);
        return converterParaDTO(emprestimo);
    }

    @Transactional
    public void excluir(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        if ("ATIVO".equals(emprestimo.getStatus())) {
            // Incrementar quantidade disponível do livro
            Livro livro = emprestimo.getLivro();
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
            livroRepository.save(livro);
        }

        emprestimoRepository.deleteById(id);
    }

    private EmprestimoDTO converterParaDTO(Emprestimo emprestimo) {
        return new EmprestimoDTO(
                emprestimo.getId(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucaoPrevista(),
                emprestimo.getDataDevolucaoReal(),
                emprestimo.getStatus(),
                emprestimo.getUsuario().getId(),
                emprestimo.getLivro().getId()
        );
    }
}