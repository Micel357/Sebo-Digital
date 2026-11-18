package com.sebo.service;

import com.sebo.models.Funcionario;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FuncionarioServiceTest {
    private FuncionarioService funcionarioService;

    @Before
    public void setUp() {
        funcionarioService = new FuncionarioService();
    }

    // Cenário 10: Cadastro de novo funcionário com sucesso
    @Test
    public void testCenario10_CadastroFuncionarioComSucesso() throws Exception {
        Funcionario funcionario = new Funcionario("Neymar Junior", "12345988901", "Atendente");
        
        boolean resultado = funcionarioService.adicionarFuncionario(funcionario);
        
        assertTrue("Funcionário deve ser cadastrado com sucesso", resultado);
        Funcionario encontrado = funcionarioService.buscarPorCPF("12345988901");
        assertNotNull("Funcionário deve ser encontrado", encontrado);
        assertEquals("Nome deve ser Neymar Junior", "Neymar Junior", encontrado.getNome());
    }

    // Cenário 11: Tentativa de cadastro com CPF duplicado
    @Test
    public void testCenario11_CadastroComCPFDuplicado() throws Exception {
        Funcionario func1 = new Funcionario("Neymar Junior", "12345988901", "Atendente");
        Funcionario func2 = new Funcionario("Outro Funcionário", "12345988901", "Gerente");
        
        funcionarioService.adicionarFuncionario(func1);
        
        try {
            funcionarioService.adicionarFuncionario(func2);
            fail("Deve lançar exceção para CPF duplicado");
        } catch (Exception e) {
            assertEquals("Mensagem de erro deve indicar CPF cadastrado", "CPF já cadastrado", e.getMessage());
        }
    }

    // Cenário 12: Campos obrigatórios faltando
    @Test
    public void testCenario12_CamposObrigatoriosFaltando() throws Exception {
        Funcionario func1 = new Funcionario(null, "12345988901", "Atendente");
        
        try {
            funcionarioService.adicionarFuncionario(func1);
            fail("Deve lançar exceção para nome faltando");
        } catch (Exception e) {
            assertTrue("Mensagem deve mencionar campos obrigatórios", 
                      e.getMessage().contains("obrigatório"));
        }

        Funcionario func2 = new Funcionario("João Silva", null, "Atendente");
        try {
            funcionarioService.adicionarFuncionario(func2);
            fail("Deve lançar exceção para CPF faltando");
        } catch (Exception e) {
            assertTrue("Mensagem deve mencionar campos obrigatórios", 
                      e.getMessage().contains("obrigatório"));
        }

        Funcionario func3 = new Funcionario("João Silva", "12345988901", null);
        try {
            funcionarioService.adicionarFuncionario(func3);
            fail("Deve lançar exceção para função faltando");
        } catch (Exception e) {
            assertTrue("Mensagem deve mencionar campos obrigatórios", 
                      e.getMessage().contains("obrigatório"));
        }
    }
}
