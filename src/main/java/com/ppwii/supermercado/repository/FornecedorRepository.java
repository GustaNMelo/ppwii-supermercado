package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.Fornecedor;
import com.ppwii.supermercado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    @Query("SELECT COUNT(p) > 0 FROM Produto p JOIN p.fornecedores f WHERE f = :fornecedor")
    boolean existsInProduto(@Param("fornecedor") Fornecedor fornecedor);
}
