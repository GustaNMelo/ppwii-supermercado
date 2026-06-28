package com.ppwii.supermercado.controller;

import com.ppwii.supermercado.model.ItemVenda;
import com.ppwii.supermercado.model.Venda;
import com.ppwii.supermercado.repository.UserRepository;
import com.ppwii.supermercado.service.IClienteService;
import com.ppwii.supermercado.service.IProdutoService;
import com.ppwii.supermercado.service.IVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VendaController {

    @Autowired
    private IVendaService vendaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/venda")
    public String listVendas(Model model) {
        model.addAttribute("vendas", vendaService.getAllVendas());
        return "venda/list";
    }

    @GetMapping("/venda/create")
    public String showCreateForm(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "venda/form";
    }

    @PostMapping("/venda/save")
    public String saveVenda(@RequestParam Integer clienteId,
                            @RequestParam List<Integer> produtoIds,
                            @RequestParam List<Integer> quantidades,
                            @AuthenticationPrincipal UserDetails userDetails) {

        Venda venda = new Venda();
        venda.setCliente(clienteService.getClienteById(clienteId));
        venda.setUsuario(userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        venda.setData(LocalDateTime.now());

        List<ItemVenda> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < produtoIds.size(); i++) {
            var produto = produtoService.getProdutoById(produtoIds.get(i));
            int qtd = quantidades.get(i);
            if (qtd <= 0) continue;

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(qtd));
            total = total.add(subtotal);

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(qtd);
            item.setSubtotal(subtotal);
            itens.add(item);
        }

        venda.setTotal(total);
        venda.setItens(itens);
        vendaService.saveVenda(venda);

        return "redirect:/venda";
    }

    @GetMapping("/venda/delete/{id}")
    public String deleteVenda(@PathVariable Integer id) {
        vendaService.deleteVendaById(id);
        return "redirect:/venda";
    }
}
