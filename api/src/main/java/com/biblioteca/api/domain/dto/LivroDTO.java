package com.biblioteca.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Long id;

    @NotBlank(message = "Título não pode ser vazio")
    private String titulo;

    @NotBlank(message = "ISBN não pode ser vazio")
    private String isbn;

    private String descricao;

    private Integer anoPublicacao;

    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidadeTotal;

    @Positive(message = "Quantidade disponível deve ser maior ou igual a zero")
    private Integer quantidadeDisponivel;

    private Long autorId;

    private Long categoriaId;
}
