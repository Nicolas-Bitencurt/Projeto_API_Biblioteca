package com.biblioteca.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode ser vazio")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "ISBN não pode ser vazio")
    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false)
    private Integer anoPublicacao;

    @Positive(message = "Quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantidadeTotal;

    @Positive(message = "Quantidade disponível deve ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer quantidadeDisponivel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprestimo> emprestimos;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}