package com.biblioteca.api.application.service;

import com.biblioteca.api.domain.dto.CategoriaDTO;
import com.biblioteca.api.domain.entity.Categoria;
import com.biblioteca.api.domain.repository.CategoriaRepository;
import com.biblioteca.api.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaDTO criar(CategoriaDTO dto) {
        if (categoriaRepository.findByNomeIgnoreCase(dto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Categoria com este nome já existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        categoriaRepository.save(categoria);
        return converterParaDTO(categoria);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO obterPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return converterParaDTO(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> obterTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        categoriaRepository.save(categoria);
        return converterParaDTO(categoria);
    }

    @Transactional
    public void excluir(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO converterParaDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}