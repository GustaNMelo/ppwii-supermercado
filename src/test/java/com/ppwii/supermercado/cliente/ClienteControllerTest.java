package com.ppwii.supermercado.cliente;

import com.ppwii.supermercado.config.SecurityConfig;
import com.ppwii.supermercado.config.TestConfig;
import com.ppwii.supermercado.controller.ClienteController;
import com.ppwii.supermercado.model.Cliente;
import com.ppwii.supermercado.service.IClienteService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(ClienteController.class)
@Import({TestConfig.class, SecurityConfig.class})
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IClienteService clienteService;

    @AfterEach
    void resetMocks() {
        Mockito.reset(clienteService);
    }

    private Cliente criarCliente() {
        Cliente c = new Cliente();
        c.setId(1);
        c.setNome("João da Silva");
        c.setCpf("123.456.789-09");
        c.setEmail("joao@email.com");
        c.setTelefone("(34) 99999-0000");
        return c;
    }

    // ─── GET /cliente ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Listar clientes sem login deve redirecionar para o login")
    void testListarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    @DisplayName("Listar clientes com login deve exibir a lista com os clientes cadastrados")
    void testListarComLogin() throws Exception {
        Mockito.when(clienteService.getAllClientes()).thenReturn(List.of(criarCliente()));

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cliente/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("clientes"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("João da Silva")));
    }

    // ─── GET /cliente/create ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir formulário de criação sem login deve redirecionar para o login")
    void testFormCriarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir formulário de criação com login deve exibir formulário vazio")
    void testFormCriarComLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cliente/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cliente"));
    }

    // ─── POST /cliente/save ───────────────────────────────────────────────────────

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Salvar cliente com dados válidos deve redirecionar para a listagem")
    void testSalvarClienteValido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("nome", "João da Silva")
                        .param("cpf", "123.456.789-09")
                        .param("email", "joao@email.com")
                        .param("telefone", "(34) 99999-0000"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cliente"));

        Mockito.verify(clienteService).saveCliente(ArgumentMatchers.any(Cliente.class));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Salvar cliente com dados inválidos deve retornar o formulário com erros de validação")
    void testSalvarClienteInvalido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("nome", "")
                        .param("cpf", "")
                        .param("email", "email-invalido")
                        .param("telefone", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cliente/form"))
                .andExpect(MockMvcResultMatchers.model().hasErrors());

        Mockito.verify(clienteService, Mockito.never()).saveCliente(ArgumentMatchers.any());
    }

    // ─── GET /cliente/edit/{id} ───────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir edição de cliente sem login deve redirecionar para o login")
    void testFormEditarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/edit/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir edição de cliente com login deve carregar os dados no formulário")
    void testFormEditarComLogin() throws Exception {
        Cliente cliente = criarCliente();
        Mockito.when(clienteService.getClienteById(1)).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cliente/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cliente"))
                .andExpect(MockMvcResultMatchers.model().attribute("cliente", cliente));
    }

    // ─── GET /cliente/delete/{id} ─────────────────────────────────────────────────

    @Test
    @DisplayName("Excluir cliente sem login deve redirecionar para o login")
    void testExcluirSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Excluir cliente com login deve remover o registro e redirecionar para a listagem")
    void testExcluirComLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cliente"));

        Mockito.verify(clienteService).deleteClienteById(1);
    }
}
