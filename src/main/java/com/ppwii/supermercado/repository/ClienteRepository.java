package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
