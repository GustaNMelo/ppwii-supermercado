package com.ppwii.supermercado.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fornecedor_id")
    private Integer id;

    @Column(name = "fornecedor_nome", nullable = false)
    private String nome;

    @Column(name = "fornecedor_cnpj")
    private String cnpj;

    @Column(name = "fornecedor_email")
    private String email;

    @Column(name = "fornecedor_telefone")
    private String telefone;
}
