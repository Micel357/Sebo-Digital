package com.sebo.service;

import com.sebo.models.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.sebo.testing.JUnit5Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class LivroServiceTest {
    private LivroService livroService;
    private FuncionarioService funcionarioService;

    @BeforeEach
    public void setUp() throws Exception {
        funcionarioService = mock(FuncionarioService.class);
        when(funcionarioService.existeFuncionario(1)).thenReturn(true);
        when(funcionarioService.existeFuncionario(9999)).thenReturn(false);
        livroService = new LivroService(funcionarioService);
    }

    // Cenário 13: Cadastro de novo livro com sucesso
    @Test
    public void testCenario13_CadastroLivroComSucesso() throws Exception {
        Livro livro = new Livro("O Senhor dos Anéis", 2012, "J.R.R. Tolkien", "Ótimo", 1);
        
        boolean resultado = livroService.adicionarLivro(livro);
        
        assertTrue("Livro deve ser cadastrado com sucesso", resultado);
        Livro encontrado = livroService.buscarPorTitulo("O Senhor dos Anéis");
        assertNotNull("Livro deve ser encontrado", encontrado);
        assertEquals("Autor deve ser J.R.R. Tolkien", "J.R.R. Tolkien", encontrado.getAutor());
    }

    // Cenário 14: Cadastro sem funcionário associado
    @Test
    public void testCenario14_CadastroSemFuncionarioAssociado() throws Exception {
        Livro livro = new Livro("Outro Livro", 2020, "Autor", "Bom", 0);
        
        try {
            livroService.adicionarLivro(livro);
            fail("Deve lançar exceção para funcionário não informado");
        } catch (Exception e) {
            assertEquals("Mensagem de erro deve indicar código obrigatório", 
                        "Código do funcionário é obrigatório", e.getMessage());
        }
    }

    // Cenário 15: Cadastro com funcionário inexistente
    @Test
    public void testCenario15_CadastroComFuncionarioInexistente() throws Exception {
        Livro livro = new Livro("Livro Teste", 2020, "Autor Teste", "Bom", 9999);
        
        try {
            livroService.adicionarLivro(livro);
            fail("Deve lançar exceção para funcionário inexistente");
        } catch (Exception e) {
            assertEquals("Mensagem de erro deve indicar funcionário não encontrado", 
                        "Funcionário não encontrado", e.getMessage());
        }
    }

    // Cenário 16: Atualizar estado de conservação de um livro
    @Test
    public void testCenario16_AtualizarEstadoLivro() throws Exception {
        Livro livro = new Livro("1984", 2015, "George Orwell", "Regular", 1);
        livroService.adicionarLivro(livro);
        
        boolean resultado = livroService.atualizarEstado("1984", "Bom");
        
        assertTrue("Atualização deve ser bem-sucedida", resultado);
        Livro atualizado = livroService.buscarPorTitulo("1984");
        assertEquals("Estado deve ser atualizado para Bom", "Bom", atualizado.getEstado());
    }
}
