package com.ppwii.supermercado.produto;

import com.ppwii.supermercado.config.SecurityConfig;
import com.ppwii.supermercado.config.TestConfig;
import com.ppwii.supermercado.controller.ProdutoController;
import com.ppwii.supermercado.model.Categoria;
import com.ppwii.supermercado.model.Fornecedor;
import com.ppwii.supermercado.model.Produto;
import com.ppwii.supermercado.service.ICategoriaService;
import com.ppwii.supermercado.service.IFornecedorService;
import com.ppwii.supermercado.service.IProdutoService;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProdutoController.class)
@Import({TestConfig.class, SecurityConfig.class})
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProdutoService produtoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IFornecedorService fornecedorService;

    @AfterEach
    void resetMocks() {
        Mockito.reset(produtoService, categoriaService, fornecedorService);
    }

    private Produto criarProduto() {
        Produto p = new Produto();
        p.setId(1);
        p.setNome("Maçã");
        p.setPreco(new BigDecimal("3.50"));
        p.setDescricao("Maçã fuji");
        p.setFornecedores(new ArrayList<>());

        Categoria c = new Categoria();
        c.setId(1);
        c.setNome("Hortifruti");
        p.setCategoria(c);

        return p;
    }

    private List<Categoria> criarCategorias() {
        Categoria c = new Categoria();
        c.setId(1);
        c.setNome("Hortifruti");
        return List.of(c);
    }

    private List<Fornecedor> criarFornecedores() {
        Fornecedor f = new Fornecedor();
        f.setId(1);
        f.setNome("Distribuidora A");
        return List.of(f);
    }

    // ─── GET /produto ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Listar produtos sem login deve redirecionar para o login")
    void testListarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    @DisplayName("Listar produtos com login deve exibir a lista com os produtos cadastrados")
    void testListarComLogin() throws Exception {
        Mockito.when(produtoService.getAllProdutos()).thenReturn(List.of(criarProduto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/produto"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("produto/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Maçã")));
    }

    // ─── GET /produto/create ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir formulário de criação sem login deve redirecionar para o login")
    void testFormCriarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir formulário de criação com login deve exibir formulário com categorias e fornecedores")
    void testFormCriarComLogin() throws Exception {
        Mockito.when(categoriaService.getAllCategorias()).thenReturn(criarCategorias());
        Mockito.when(fornecedorService.getAllFornecedores()).thenReturn(criarFornecedores());

        mockMvc.perform(MockMvcRequestBuilders.get("/produto/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("produto/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("produto"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categorias"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("fornecedores"));
    }

    // ─── POST /produto/save ───────────────────────────────────────────────────────

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Salvar novo produto deve redirecionar para a listagem")
    void testSalvarProduto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/produto/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("nome", "Banana")
                        .param("preco", "2.50")
                        .param("descricao", "Banana prata"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/produto"));

        Mockito.verify(produtoService).saveProduto(ArgumentMatchers.any(Produto.class));
    }

    // ─── GET /produto/edit/{id} ───────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir edição de produto sem login deve redirecionar para o login")
    void testFormEditarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/edit/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir edição de produto com login deve carregar os dados no formulário")
    void testFormEditarComLogin() throws Exception {
        Produto produto = criarProduto();
        Mockito.when(produtoService.getProdutoById(1)).thenReturn(produto);
        Mockito.when(categoriaService.getAllCategorias()).thenReturn(criarCategorias());
        Mockito.when(fornecedorService.getAllFornecedores()).thenReturn(criarFornecedores());

        mockMvc.perform(MockMvcRequestBuilders.get("/produto/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("produto/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("produto"))
                .andExpect(MockMvcResultMatchers.model().attribute("produto", produto));
    }

    // ─── GET /produto/delete/{id} ─────────────────────────────────────────────────

    @Test
    @DisplayName("Excluir produto sem login deve redirecionar para o login")
    void testExcluirSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Excluir produto com login deve remover o registro e redirecionar para a listagem")
    void testExcluirComLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/produto"));

        Mockito.verify(produtoService).deleteProdutoById(1);
    }
}
