package com.biblioteca.api.application.service;

import com.biblioteca.api.domain.dto.AutorDTO;
import com.biblioteca.api.domain.entity.Autor;
import com.biblioteca.api.domain.repository.AutorRepository;
import com.biblioteca.api.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional
    public AutorDTO criar(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.getNome());
        autor.setBiografia(dto.getBiografia());

        autorRepository.save(autor);
        return converterParaDTO(autor);
    }

    @Transactional(readOnly = true)
    public AutorDTO obterPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));
        return converterParaDTO(autor);
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> obterTodos() {
        return autorRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> buscarPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));

        autor.setNome(dto.getNome());
        autor.setBiografia(dto.getBiografia());

        autorRepository.save(autor);
        return converterParaDTO(autor);
    }

    @Transactional
    public void excluir(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }

    private AutorDTO converterParaDTO(Autor autor) {
        return new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getBiografia()
        );
    }
}
