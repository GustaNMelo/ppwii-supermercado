package com.ppwii.supermercado.controller;

import com.ppwii.supermercado.model.Fornecedor;
import com.ppwii.supermercado.service.IFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FornecedorController {

    @Autowired
    private IFornecedorService fornecedorService;

    @GetMapping("/fornecedor")
    public String listFornecedores(Model model) {
        model.addAttribute("fornecedores", fornecedorService.getAllFornecedores());
        return "fornecedor/list";
    }

    @GetMapping("/fornecedor/create")
    public String showCreateForm(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor/form";
    }

    @GetMapping("/fornecedor/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("fornecedor", fornecedorService.getFornecedorById(id));
        return "fornecedor/form";
    }

    @PostMapping("/fornecedor/save")
    public String saveFornecedor(@ModelAttribute Fornecedor fornecedor) {
        fornecedorService.saveFornecedor(fornecedor);
        return "redirect:/fornecedor";
    }

    @GetMapping("/fornecedor/delete/{id}")
    public String deleteFornecedor(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            fornecedorService.deleteFornecedorById(id);
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/fornecedor";
    }
}
