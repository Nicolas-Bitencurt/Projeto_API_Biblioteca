package com.biblioteca.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCriacaoDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email não pode ser vazio")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

    @NotBlank(message = "Funcao não pode ser vazia")
    private String funcao;
}
