package com.biblioteca.api.presentation.controller;

import com.biblioteca.api.application.service.AuthenticationService;
import com.biblioteca.api.domain.dto.LoginDTO;
import com.biblioteca.api.domain.dto.TokenDTO;
import com.biblioteca.api.domain.dto.UsuarioCriacaoDTO;
import com.biblioteca.api.domain.dto.UsuarioDTO;
import com.biblioteca.api.application.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e autorização")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    public AuthenticationController(AuthenticationService authenticationService, UsuarioService usuarioService) {
        this.authenticationService = authenticationService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioCriacaoDTO dto) {
        UsuarioDTO usuarioDTO = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "Fazer login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        TokenDTO tokenDTO = authenticationService.authenticate(dto);
        return ResponseEntity.ok(tokenDTO);
    }
}
