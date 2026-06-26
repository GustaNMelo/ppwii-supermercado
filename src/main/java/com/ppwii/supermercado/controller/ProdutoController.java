package com.ppwii.supermercado.controller;

import com.ppwii.supermercado.model.Produto;
import com.ppwii.supermercado.service.ICategoriaService;
import com.ppwii.supermercado.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProdutoController {

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/produto")
    public String listProdutos(Model model) {
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "produto/list";
    }

    @GetMapping("/produto/create")
    public String showCreateForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "produto/form";
    }

    @GetMapping("/produto/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("produto", produtoService.getProdutoById(id));
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "produto/form";
    }

    @PostMapping("/produto/save")
    public String saveProduto(@ModelAttribute Produto produto) {
        produtoService.saveProduto(produto);
        return "redirect:/produto";
    }

    @GetMapping("/produto/delete/{id}")
    public String deleteProduto(@PathVariable Integer id) {
        produtoService.deleteProdutoById(id);
        return "redirect:/produto";
    }
}
