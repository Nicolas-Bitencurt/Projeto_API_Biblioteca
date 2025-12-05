package com.biblioteca.api.presentation.controller;

import com.biblioteca.api.application.service.LivroService;
import com.biblioteca.api.domain.dto.LivroDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Livros", description = "Gerenciamento de livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @Operation(summary = "Criar novo livro")
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO dto) {
        LivroDTO livroDTO = livroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        List<LivroDTO> livros = livroService.obterTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter livro por ID")
    public ResponseEntity<LivroDTO> obterPorId(@PathVariable Long id) {
        LivroDTO livro = livroService.obterPorId(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/buscar/titulo")
    @Operation(summary = "Buscar livros por título")
    public ResponseEntity<List<LivroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<LivroDTO> livros = livroService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar/autor/{autorId}")
    @Operation(summary = "Buscar livros por autor")
    public ResponseEntity<List<LivroDTO>> buscarPorAutor(@PathVariable Long autorId) {
        List<LivroDTO> livros = livroService.buscarPorAutor(autorId);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar/categoria/{categoriaId}")
    @Operation(summary = "Buscar livros por categoria")
    public ResponseEntity<List<LivroDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {
        List<LivroDTO> livros = livroService.buscarPorCategoria(categoriaId);
        return ResponseEntity.ok(livros);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
        LivroDTO livroAtualizado = livroService.atualizar(id, dto);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir livro")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
