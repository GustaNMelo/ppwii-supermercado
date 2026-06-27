package com.ppwii.supermercado.service;

import com.ppwii.supermercado.model.Fornecedor;

import java.util.List;

public interface IFornecedorService {
    Integer saveFornecedor(Fornecedor fornecedor);
    List<Fornecedor> getAllFornecedores();
    Fornecedor getFornecedorById(Integer id);
    void deleteFornecedorById(Integer id);
    List<Fornecedor> getFornecedoresByIds(List<Integer> ids);
}
