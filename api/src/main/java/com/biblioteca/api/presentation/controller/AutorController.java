package com.biblioteca.api.presentation.controller;

import com.biblioteca.api.application.service.AutorService;
import com.biblioteca.api.domain.dto.AutorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Autores", description = "Gerenciamento de autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    @Operation(summary = "Criar novo autor")
    public ResponseEntity<AutorDTO> criar(@Valid @RequestBody AutorDTO dto) {
        AutorDTO autorDTO = autorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os autores")
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        List<AutorDTO> autores = autorService.obterTodos();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter autor por ID")
    public ResponseEntity<AutorDTO> obterPorId(@PathVariable Long id) {
        AutorDTO autor = autorService.obterPorId(id);
        return ResponseEntity.ok(autor);
    }

    @GetMapping("/buscar/nome")
    @Operation(summary = "Buscar autores por nome")
    public ResponseEntity<List<AutorDTO>> buscarPorNome(@RequestParam String nome) {
        List<AutorDTO> autores = autorService.buscarPorNome(nome);
        return ResponseEntity.ok(autores);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AutorDTO dto) {
        AutorDTO autorAtualizado = autorService.atualizar(id, dto);
        return ResponseEntity.ok(autorAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir autor")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        autorService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
