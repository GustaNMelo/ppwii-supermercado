package com.ppwii.supermercado.impl;

import com.ppwii.supermercado.model.Categoria;
import com.ppwii.supermercado.repository.CategoriaRepository;
import com.ppwii.supermercado.repository.ProdutoRepository;
import com.ppwii.supermercado.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Integer saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria).getId();
    }

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + id));
    }

    @Override
    public void deleteCategoriaById(Integer id) {
        Categoria categoria = getCategoriaById(id);
        if (produtoRepository.existsByCategoria(categoria)) {
            throw new RuntimeException("Não é possível excluir esta categoria pois existem produtos associados a ela.");
        }
        categoriaRepository.deleteById(id);
    }
}
