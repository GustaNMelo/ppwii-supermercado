package com.ppwii.supermercado.impl;

import com.ppwii.supermercado.model.Produto;
import com.ppwii.supermercado.repository.ItemVendaRepository;
import com.ppwii.supermercado.repository.ProdutoRepository;
import com.ppwii.supermercado.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements IProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Override
    public Integer saveProduto(Produto produto) {
        return produtoRepository.save(produto).getId();
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto getProdutoById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    @Override
    public void deleteProdutoById(Integer id) {
        Produto produto = getProdutoById(id);
        if (itemVendaRepository.existsByProduto(produto)) {
            throw new RuntimeException("Não é possível excluir este produto pois ele está associado a uma ou mais vendas.");
        }
        produtoRepository.deleteById(id);
    }
}
