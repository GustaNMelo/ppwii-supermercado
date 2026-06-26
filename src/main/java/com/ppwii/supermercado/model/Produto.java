package com.ppwii.supermercado.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Integer id;

    @Column(name = "produto_nome", nullable = false)
    private String nome;

    @Column(name = "produto_preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "produto_descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
