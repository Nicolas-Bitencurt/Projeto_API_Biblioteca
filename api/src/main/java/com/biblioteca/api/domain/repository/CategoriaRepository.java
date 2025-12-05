package com.biblioteca.api.domain.repository;

import com.biblioteca.api.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
