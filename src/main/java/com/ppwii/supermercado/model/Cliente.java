package com.ppwii.supermercado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "cliente_nome", nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF incompleto. Use o formato 000.000.000-00")
    @Column(name = "cliente_cpf")
    private String cpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(name = "cliente_email")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "cliente_telefone")
    private String telefone;
}
