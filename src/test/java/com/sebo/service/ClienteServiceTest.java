package com.sebo.service;

import com.sebo.models.Cliente;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteServiceTest {
    private ClienteService clienteService;

    @Before
    public void setUp() {
        clienteService = new ClienteService();
    }

    // Cenário 1: Cadastro de novo cliente com sucesso (Todos os campos)
    @Test
    public void testCenario1_CadastroClienteComSucesso() throws Exception {
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao.silva@email.com", "81998765432");
        
        boolean resultado = clienteService.cadastrarCliente(cliente);
        
        assertTrue("Cliente deve ser cadastrado com sucesso", resultado);
        assertNotNull("Cliente deve ser encontrado", clienteService.buscarPorCPF("12345678901"));
        assertEquals("Nome deve ser João Silva", "João Silva", clienteService.buscarPorCPF("12345678901").getNome());
    }

    // Cenário 2: Cadastro de cliente sem telefone (Campo opcional com valor padrão)
    @Test
    public void testCenario2_CadastroSemTelefone() throws Exception {
        Cliente cliente = new Cliente("Maria Oliveira", "11499552440", "maria.oliveira@email.com", null);
        
        boolean resultado = clienteService.cadastrarCliente(cliente);
        
        assertTrue("Cliente deve ser cadastrado", resultado);
        Cliente clienteEncontrado = clienteService.buscarPorCPF("11499552440");
        assertNotNull("Telefone deve ser preenchido com valor padrão", clienteEncontrado.getTelefone());
        assertEquals("Telefone padrão deve ser 81900000000", "81900000000", clienteEncontrado.getTelefone());
    }

    // Cenário 3: Tentativa de cadastro de cliente com CPF duplicado
    @Test
    public void testCenario3_CadastroComCPFDuplicado() throws Exception {
        Cliente cliente1 = new Cliente("João Silva", "12345678901", "joao@email.com", "81999999999");
        Cliente cliente2 = new Cliente("Ana Costa", "12345678901", "ana@email.com", "81988888888");
        
        clienteService.cadastrarCliente(cliente1);
        
        try {
            clienteService.cadastrarCliente(cliente2);
            fail("Deve lançar exceção para CPF duplicado");
        } catch (Exception e) {
            assertEquals("Mensagem de erro deve indicar CPF existente", "CPF já existe", e.getMessage());
        }
    }

    // Cenário 4: Tentativa de cadastro com campos obrigatórios faltando
    @Test
    public void testCenario4_CadastroComCamposObrigatoriosFaltando() throws Exception {
        Cliente cliente = new Cliente(null, "12345678901", "email@email.com", "81999999999");
        
        try {
            clienteService.cadastrarCliente(cliente);
            fail("Deve lançar exceção para nome faltando");
        } catch (Exception e) {
            assertEquals("Nome é obrigatório", "Nome é obrigatório", e.getMessage());
        }

        Cliente cliente2 = new Cliente("João Silva", null, "email@email.com", "81999999999");
        try {
            clienteService.cadastrarCliente(cliente2);
            fail("Deve lançar exceção para CPF faltando");
        } catch (Exception e) {
            assertEquals("CPF é obrigatório", "CPF é obrigatório", e.getMessage());
        }

        Cliente cliente3 = new Cliente("João Silva", "12345678901", null, "81999999999");
        try {
            clienteService.cadastrarCliente(cliente3);
            fail("Deve lançar exceção para email faltando");
        } catch (Exception e) {
            assertEquals("E-mail é obrigatório", "E-mail é obrigatório", e.getMessage());
        }
    }

    // Cenário 5: Tentativa de cadastro com CPF fora do formato
    @Test
    public void testCenario5_CadastroComCPFForaDoFormato() throws Exception {
        Cliente cliente = new Cliente("João Silva", "123456", "joao@email.com", "81999999999");
        
        try {
            clienteService.cadastrarCliente(cliente);
            fail("Deve lançar exceção para CPF com formato inválido");
        } catch (Exception e) {
            assertEquals("O CPF deve conter exatamente 11 dígitos", "O CPF deve conter exatamente 11 dígitos", e.getMessage());
        }
    }

    // Cenário 6: Buscar cliente por CPF (Cliente existente)
    @Test
    public void testCenario6_BuscarClientePorCPFExistente() throws Exception {
        Cliente cliente = new Cliente("Carlos Souza", "45678912301", "carlos@email.com", "81987654321");
        clienteService.cadastrarCliente(cliente);
        
        Cliente encontrado = clienteService.buscarPorCPF("45678912301");
        
        assertNotNull("Cliente deve ser encontrado", encontrado);
        assertEquals("Nome deve ser Carlos Souza", "Carlos Souza", encontrado.getNome());
    }

    // Cenário 7: Buscar cliente por CPF (Cliente inexistente)
    @Test
    public void testCenario7_BuscarClientePorCPFInexistente() throws Exception {
        Cliente encontrado = clienteService.buscarPorCPF("00000000000");
        
        assertNull("Cliente não deve ser encontrado", encontrado);
    }

    // Cenário 8: Atualizar dados de um cliente
    @Test
    public void testCenario8_AtualizarDadosCliente() throws Exception {
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com", "81999999999");
        clienteService.cadastrarCliente(cliente);
        
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setEmail("joao.novo@email.com");
        
        boolean resultado = clienteService.atualizarCliente("12345678901", clienteAtualizado);
        
        assertTrue("Atualização deve ser bem-sucedida", resultado);
        Cliente buscado = clienteService.buscarPorCPF("12345678901");
        assertEquals("E-mail deve ser atualizado", "joao.novo@email.com", buscado.getEmail());
    }

    // Cenário 9: Remover um cliente
    @Test
    public void testCenario9_RemoverCliente() throws Exception {
        Cliente cliente = new Cliente("Pedro Henrique", "78945612308", "pedro@email.com", "81988888888");
        clienteService.cadastrarCliente(cliente);
        
        boolean resultado = clienteService.removerCliente("78945612308");
        
        assertTrue("Remoção deve ser bem-sucedida", resultado);
        Cliente buscado = clienteService.buscarPorCPF("78945612308");
        assertNull("Cliente não deve mais existir", buscado);
    }
}
