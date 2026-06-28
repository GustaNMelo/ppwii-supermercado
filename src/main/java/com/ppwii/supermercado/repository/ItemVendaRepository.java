package com.ppwii.supermercado.repository;

import com.ppwii.supermercado.model.ItemVenda;
import com.ppwii.supermercado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {
    boolean existsByProduto(Produto produto);
}
