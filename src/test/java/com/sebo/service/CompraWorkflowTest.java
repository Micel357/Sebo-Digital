package com.sebo.service;

import com.sebo.models.Cliente;
import com.sebo.models.Compra;
import com.sebo.models.Funcionario;
import com.sebo.models.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CompraWorkflowTest {
    private ClienteService clienteService;
    private CompraService compraService;

    @BeforeEach
    void setUp() throws Exception {
        clienteService = new ClienteService();
        FuncionarioService funcionarioService = new FuncionarioService();
        LivroService livroService = new LivroService(funcionarioService);
        compraService = new CompraService(clienteService, livroService);

        clienteService.cadastrarCliente(
                new Cliente("Ana Souza", "52998224725", "ana@example.com", null));
        funcionarioService.adicionarFuncionario(
                new Funcionario("Caio Lima", "11144477735", "Atendente"));
        livroService.adicionarLivro(
                new Livro("O Cortiço", 1890, "Aluísio Azevedo", "Bom", 1));
    }

    @Test
    void compraValidaAsEntidadesERegistraStatusAtualDoServico() throws Exception {
        Compra compra = new Compra("52998224725", "O Cortiço", 1, 25.00,
                "Dinheiro", LocalDateTime.now(), "PENDENTE");

        assertTrue(compraService.adicionarCompra(compra));
        assertEquals("PAGA", compra.getStatus());
        assertEquals(1, compraService.getTotalCompras());
    }

    @Test
    void compraParaLivroInexistenteFalhaSemRegistrarCompra() {
        Compra compra = new Compra("52998224725", "Livro ausente", 1, 25.00,
                "Dinheiro", LocalDateTime.now(), "PENDENTE");

        Exception erro = assertThrows(Exception.class, () -> compraService.adicionarCompra(compra));

        assertEquals("Livro não encontrado", erro.getMessage());
        assertEquals(0, compraService.getTotalCompras());
    }

    @Test
    void buscaDeHistoricoRetornaSomenteComprasDoCpfSolicitado() throws Exception {
        compraService.adicionarCompra(new Compra("52998224725", "O Cortiço", 1, 25.00,
                "Dinheiro", LocalDateTime.now(), "PENDENTE"));

        assertEquals(1, compraService.buscarCompraPorCPF("529.982.247-25").size());
        assertTrue(compraService.buscarCompraPorCPF("12345678901").isEmpty());
    }
}
