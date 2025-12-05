package com.biblioteca.api.domain.repository;

import com.biblioteca.api.domain.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuarioId(Long usuarioId);

    List<Emprestimo> findByLivroId(Long livroId);

    List<Emprestimo> findByStatus(String status);

    List<Emprestimo> findByUsuarioIdAndStatus(Long usuarioId, String status);
}
