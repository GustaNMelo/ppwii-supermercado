package com.ppwii.supermercado.controller;

import com.ppwii.supermercado.model.Produto;
import com.ppwii.supermercado.service.ICategoriaService;
import com.ppwii.supermercado.service.IFornecedorService;
import com.ppwii.supermercado.service.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IFornecedorService fornecedorService;

    @GetMapping("/produto")
    public String listProdutos(Model model) {
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "produto/list";
    }

    @GetMapping("/produto/create")
    public String showCreateForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        model.addAttribute("fornecedores", fornecedorService.getAllFornecedores());
        model.addAttribute("produtoFornecedorIds", new ArrayList<>());
        return "produto/form";
    }

    @GetMapping("/produto/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Produto produto = produtoService.getProdutoById(id);
        List<Integer> produtoFornecedorIds = produto.getFornecedores() != null
                ? produto.getFornecedores().stream().map(f -> f.getId()).toList()
                : new ArrayList<>();
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        model.addAttribute("fornecedores", fornecedorService.getAllFornecedores());
        model.addAttribute("produtoFornecedorIds", produtoFornecedorIds);
        return "produto/form";
    }

    @PostMapping("/produto/save")
    public String saveProduto(@ModelAttribute Produto produto,
                              @RequestParam(required = false) List<Integer> fornecedorIds) {
        if (fornecedorIds != null && !fornecedorIds.isEmpty()) {
            produto.setFornecedores(fornecedorService.getFornecedoresByIds(fornecedorIds));
        } else {
            produto.setFornecedores(new ArrayList<>());
        }
        produtoService.saveProduto(produto);
        return "redirect:/produto";
    }

    @GetMapping("/produto/delete/{id}")
    public String deleteProduto(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            produtoService.deleteProdutoById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produto";
    }
}
