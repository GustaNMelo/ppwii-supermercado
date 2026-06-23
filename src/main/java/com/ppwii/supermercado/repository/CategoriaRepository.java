package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
