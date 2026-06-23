package com.ppwii.supermercado.service;

import com.ppwii.supermercado.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    Integer saveCategoria(Categoria categoria);
    List<Categoria> getAllCategorias();
    Categoria getCategoriaById(Integer id);
    void deleteCategoriaById(Integer id);
}
