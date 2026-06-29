package com.ppwii.supermercado.categoria;

import com.ppwii.supermercado.config.SecurityConfig;
import com.ppwii.supermercado.config.TestConfig;
import com.ppwii.supermercado.controller.CategoriaController;
import com.ppwii.supermercado.model.Categoria;
import com.ppwii.supermercado.service.ICategoriaService;

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

@WebMvcTest(CategoriaController.class)
@Import({TestConfig.class, SecurityConfig.class})
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICategoriaService categoriaService;

    @AfterEach
    void resetMocks() {
        Mockito.reset(categoriaService);
    }

    private List<Categoria> criarListaDeCategorias() {
        Categoria c = new Categoria();
        c.setId(1);
        c.setNome("Hortifruti");
        c.setDescricao("Frutas e verduras");
        return List.of(c);
    }

    // ─── GET /categoria ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Listar categorias sem login deve redirecionar para o login")
    void testListarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    @DisplayName("Listar categorias com login deve exibir a lista com os nomes cadastrados")
    void testListarComLogin() throws Exception {
        Mockito.when(categoriaService.getAllCategorias()).thenReturn(criarListaDeCategorias());

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("categoria/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categorias"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Hortifruti")));
    }

    // ─── GET /categoria/create ───────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir formulário de criação sem login deve redirecionar para o login")
    void testFormCriarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir formulário de criação com login deve exibir formulário vazio")
    void testFormCriarComLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("categoria/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoria"));
    }

    // ─── POST /categoria/save ────────────────────────────────────────────────────

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Salvar nova categoria deve redirecionar para a listagem")
    void testSalvarCategoria() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/categoria/save")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("nome", "Bebidas")
                        .param("descricao", "Sucos e refrigerantes"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/categoria"));

        Mockito.verify(categoriaService).saveCategoria(ArgumentMatchers.any(Categoria.class));
    }

    // ─── GET /categoria/edit/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("Abrir edição de categoria sem login deve redirecionar para o login")
    void testFormEditarSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/edit/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Abrir edição de categoria com login deve carregar os dados no formulário")
    void testFormEditarComLogin() throws Exception {
        Categoria categoria = criarListaDeCategorias().get(0);
        Mockito.when(categoriaService.getCategoriaById(1)).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("categoria/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoria"))
                .andExpect(MockMvcResultMatchers.model().attribute("categoria", categoria));
    }

    // ─── GET /categoria/delete/{id} ──────────────────────────────────────────────

    @Test
    @DisplayName("Excluir categoria sem login deve redirecionar para o login")
    void testExcluirSemLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(authorities = "Gerente")
    @DisplayName("Excluir categoria com login deve remover o registro e redirecionar para a listagem")
    void testExcluirComLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/categoria"));

        Mockito.verify(categoriaService).deleteCategoriaById(1);
    }
}
