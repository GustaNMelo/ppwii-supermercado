package com.ppwii.supermercado.impl;

import com.ppwii.supermercado.model.Venda;
import com.ppwii.supermercado.repository.VendaRepository;
import com.ppwii.supermercado.service.IVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaServiceImpl implements IVendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Override
    public Venda saveVenda(Venda venda) {
        return vendaRepository.save(venda);
    }

    @Override
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }

    @Override
    public Venda getVendaById(Integer id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada: " + id));
    }

    @Override
    public void deleteVendaById(Integer id) {
        vendaRepository.deleteById(id);
    }
}
