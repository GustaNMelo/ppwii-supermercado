package com.ppwii.supermercado.service;

import com.ppwii.supermercado.model.Venda;

import java.util.List;

public interface IVendaService {
    Venda saveVenda(Venda venda);
    List<Venda> getAllVendas();
    Venda getVendaById(Integer id);
    void deleteVendaById(Integer id);
}
