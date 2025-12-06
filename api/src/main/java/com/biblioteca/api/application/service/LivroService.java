package com.biblioteca.api.application.service;

import com.biblioteca.api.domain.dto.LivroDTO;
import com.biblioteca.api.domain.entity.Livro;
import com.biblioteca.api.domain.entity.Autor;
import com.biblioteca.api.domain.entity.Categoria;
import com.biblioteca.api.domain.repository.LivroRepository;
import com.biblioteca.api.domain.repository.AutorRepository;
import com.biblioteca.api.domain.repository.CategoriaRepository;
import com.biblioteca.api.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public LivroDTO criar(LivroDTO dto) {
        if (livroRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Livro com este ISBN já existe");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setDescricao(dto.getDescricao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setQuantidadeTotal(dto.getQuantidadeTotal());
        livro.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        livroRepository.save(livro);
        return converterParaDTO(livro);
    }

    @Transactional(readOnly = true)
    public LivroDTO obterPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
        return converterParaDTO(livro);
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> obterTodos() {
        return livroRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> buscarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> buscarPorCategoria(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LivroDTO atualizar(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        if (!livro.getIsbn().equals(dto.getIsbn()) && livroRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN já está em uso");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setDescricao(dto.getDescricao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setQuantidadeTotal(dto.getQuantidadeTotal());
        livro.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        livroRepository.save(livro);
        return converterParaDTO(livro);
    }

    @Transactional
    public void excluir(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }

    private LivroDTO converterParaDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getDescricao(),
                livro.getAnoPublicacao(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeDisponivel(),
                livro.getAutor().getId(),
                livro.getCategoria().getId()
        );
    }
}