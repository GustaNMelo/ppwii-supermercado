package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.Cliente;
import com.ppwii.supermercado.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    boolean existsByCliente(Cliente cliente);
}
