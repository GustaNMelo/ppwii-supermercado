package com.ppwii.supermercado.service;

import com.ppwii.supermercado.model.Cliente;

import java.util.List;

public interface IClienteService {
    Integer saveCliente(Cliente cliente);
    List<Cliente> getAllClientes();
    Cliente getClienteById(Integer id);
    void deleteClienteById(Integer id);
}
