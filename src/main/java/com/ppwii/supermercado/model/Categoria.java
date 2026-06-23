package com.ppwii.supermercado.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "categoria_nome", nullable = false)
    private String nome;

    @Column(name = "categoria_descricao")
    private String descricao;
}
