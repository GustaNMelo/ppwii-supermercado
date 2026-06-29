package com.ppwii.supermercado.config;

import com.ppwii.supermercado.service.ICategoriaService;
import com.ppwii.supermercado.service.IClienteService;
import com.ppwii.supermercado.service.IFornecedorService;
import com.ppwii.supermercado.service.IProdutoService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ICategoriaService categoriaService() {
        return Mockito.mock(ICategoriaService.class);
    }

    @Bean
    public IFornecedorService fornecedorService() {
        return Mockito.mock(IFornecedorService.class);
    }

    @Bean
    public IProdutoService produtoService() {
        return Mockito.mock(IProdutoService.class);
    }

    @Bean
    public IClienteService clienteService() {
        return Mockito.mock(IClienteService.class);
    }
}
