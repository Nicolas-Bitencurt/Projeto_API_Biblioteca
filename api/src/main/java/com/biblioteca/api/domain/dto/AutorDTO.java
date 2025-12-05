package com.biblioteca.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    private String biografia;
}
