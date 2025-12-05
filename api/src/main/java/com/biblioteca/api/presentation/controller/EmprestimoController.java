package com.biblioteca.api.presentation.controller;

import com.biblioteca.api.application.service.EmprestimoService;
import com.biblioteca.api.domain.dto.EmprestimoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Empréstimos", description = "Gerenciamento de empréstimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    @Operation(summary = "Criar novo empréstimo")
    public ResponseEntity<EmprestimoDTO> criar(@Valid @RequestBody EmprestimoDTO dto) {
        EmprestimoDTO emprestimoDTO = emprestimoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os empréstimos")
    public ResponseEntity<List<EmprestimoDTO>> listarTodos() {
        List<EmprestimoDTO> emprestimos = emprestimoService.obterTodos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter empréstimo por ID")
    public ResponseEntity<EmprestimoDTO> obterPorId(@PathVariable Long id) {
        EmprestimoDTO emprestimo = emprestimoService.obterPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/buscar/usuario/{usuarioId}")
    @Operation(summary = "Buscar empréstimos por usuário")
    public ResponseEntity<List<EmprestimoDTO>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<EmprestimoDTO> emprestimos = emprestimoService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/buscar/livro/{livroId}")
    @Operation(summary = "Buscar empréstimos por livro")
    public ResponseEntity<List<EmprestimoDTO>> buscarPorLivro(@PathVariable Long livroId) {
        List<EmprestimoDTO> emprestimos = emprestimoService.buscarPorLivro(livroId);
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/buscar/status/{status}")
    @Operation(summary = "Buscar empréstimos por status")
    public ResponseEntity<List<EmprestimoDTO>> buscarPorStatus(@PathVariable String status) {
        List<EmprestimoDTO> emprestimos = emprestimoService.buscarPorStatus(status);
        return ResponseEntity.ok(emprestimos);
    }

    @PutMapping("/{id}/devolver")
    @Operation(summary = "Devolver um empréstimo")
    public ResponseEntity<EmprestimoDTO> devolver(@PathVariable Long id) {
        EmprestimoDTO emprestimoDTO = emprestimoService.devolver(id);
        return ResponseEntity.ok(emprestimoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empréstimo")
    public ResponseEntity<EmprestimoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EmprestimoDTO dto) {
        EmprestimoDTO emprestimoAtualizado = emprestimoService.atualizar(id, dto);
        return ResponseEntity.ok(emprestimoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir empréstimo")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        emprestimoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
