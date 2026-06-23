package com.ppwii.supermercado.controller;

import com.ppwii.supermercado.model.Categoria;
import com.ppwii.supermercado.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/categoria")
    public String listCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "categoria/list";
    }

    @GetMapping("/categoria/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @GetMapping("/categoria/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("categoria", categoriaService.getCategoriaById(id));
        return "categoria/form";
    }

    @PostMapping("/categoria/save")
    public String saveCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.saveCategoria(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/categoria/delete/{id}")
    public String deleteCategoria(@PathVariable Integer id) {
        categoriaService.deleteCategoriaById(id);
        return "redirect:/categoria";
    }
}
