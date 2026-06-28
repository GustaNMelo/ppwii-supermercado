package com.ppwii.supermercado.impl;

import com.ppwii.supermercado.model.Fornecedor;
import com.ppwii.supermercado.repository.FornecedorRepository;
import com.ppwii.supermercado.service.IFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements IFornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Override
    public Integer saveFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor).getId();
    }

    @Override
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    @Override
    public Fornecedor getFornecedorById(Integer id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado: " + id));
    }

    @Override
    public void deleteFornecedorById(Integer id) {
        Fornecedor fornecedor = getFornecedorById(id);
        if (fornecedorRepository.existsInProduto(fornecedor)) {
            throw new RuntimeException("Não é possível excluir este fornecedor pois ele está associado a um ou mais produtos.");
        }
        fornecedorRepository.deleteById(id);
    }

    @Override
    public List<Fornecedor> getFornecedoresByIds(List<Integer> ids) {
        return fornecedorRepository.findAllById(ids);
    }
}
