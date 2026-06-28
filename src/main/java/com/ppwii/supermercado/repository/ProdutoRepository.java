package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.Categoria;
import com.ppwii.supermercado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    boolean existsByCategoria(Categoria categoria);
}
