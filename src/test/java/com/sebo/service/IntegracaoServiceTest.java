package com.sebo.service;

import com.sebo.models.Cliente;
import com.sebo.models.Compra;
import com.sebo.models.Funcionario;
import com.sebo.models.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IntegracaoServiceTest {
    private ClienteService clienteService;
    private FuncionarioService funcionarioService;
    private LivroService livroService;
    private CompraService compraService;

    @BeforeEach
    void setUp() throws Exception {
        clienteService = new ClienteService();
        funcionarioService = new FuncionarioService();
        livroService = new LivroService(funcionarioService);
        compraService = new CompraService(clienteService, livroService);

        clienteService.cadastrarCliente(
                new Cliente("Ana Souza", "52998224725", "ana@example.com", "81990000000"));
        funcionarioService.adicionarFuncionario(
                new Funcionario("Caio Lima", "11144477735", "Atendente"));
        livroService.adicionarLivro(
                new Livro("Dom Casmurro", 1899, "Machado de Assis", "Bom", 1));
    }

    @Test
    void clienteConsegueConsultarCompraRegistrada() throws Exception {
        Compra compra = new Compra("52998224725", "Dom Casmurro", 1, 30.00,
                "Dinheiro", LocalDateTime.now(), "PENDENTE");

        assertTrue(compraService.adicionarCompra(compra));

        Compra historico = compraService.obterRelatorioCompra("529.982.247-25", "Dom Casmurro");
        assertNotNull(historico);
        assertEquals("PAGA", historico.getStatus());
        assertEquals(1, compraService.buscarCompraPorCPF("52998224725").size());
    }

    @Test
    void atualizacaoParcialDoClientePreservaOsDemaisDados() throws Exception {
        Cliente atualizacao = new Cliente();
        atualizacao.setEmail("ana.nova@example.com");

        assertTrue(clienteService.atualizarCliente("52998224725", atualizacao));

        Cliente atualizado = clienteService.buscarPorCPF("52998224725");
        assertEquals("Ana Souza", atualizado.getNome());
        assertEquals("ana.nova@example.com", atualizado.getEmail());
        assertEquals("81990000000", atualizado.getTelefone());
    }

    @Test
    void cadastroDeClienteComCpfDuplicadoFalhaSemAlterarQuantidade() throws Exception {
        Cliente duplicado = new Cliente("Outra Ana", "52998224725", "outra@example.com", null);

        Exception erro = assertThrows(Exception.class, () -> clienteService.cadastrarCliente(duplicado));

        assertEquals("CPF já existe", erro.getMessage());
        assertEquals(1, clienteService.getTotalClientes());
    }

    @Test
    void compraDeClienteInexistenteNaoEArmazenada() {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00,
                "Dinheiro", LocalDateTime.now(), "PENDENTE");

        Exception erro = assertThrows(Exception.class, () -> compraService.adicionarCompra(compra));

        assertEquals("Cliente não cadastrado", erro.getMessage());
        assertEquals(0, compraService.getTotalCompras());
    }
}
