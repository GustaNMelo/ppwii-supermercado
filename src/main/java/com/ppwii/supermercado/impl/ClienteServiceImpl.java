package com.ppwii.supermercado.impl;

import com.ppwii.supermercado.model.Cliente;
import com.ppwii.supermercado.repository.ClienteRepository;
import com.ppwii.supermercado.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Integer saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente).getId();
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
    }

    @Override
    public void deleteClienteById(Integer id) {
        clienteRepository.deleteById(id);
    }
}
