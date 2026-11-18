package com.sebo.service;

import com.sebo.models.Cliente;
import com.sebo.models.Livro;
import com.sebo.models.Compra;
import com.sebo.models.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static com.sebo.testing.JUnit5Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class CompraServiceTest {
    private CompraService compraService;
    private ClienteService clienteService;
    private LivroService livroService;
    private FuncionarioService funcionarioService;

    @BeforeEach
    public void setUp() throws Exception {
        clienteService = new ClienteService();
        funcionarioService = new FuncionarioService();
        livroService = new LivroService(funcionarioService);
        compraService = new CompraService(clienteService, livroService);

        // Cadastra dados necessários
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com", "81999999999");
        clienteService.cadastrarCliente(cliente);

        Funcionario func = new Funcionario("Kaio Castro", "12345678902", "Estoquista");
        funcionarioService.adicionarFuncionario(func);

        Livro livro = new Livro("Dom Casmurro", 2010, "Machado de Assis", "Ótimo", 1);
        livroService.adicionarLivro(livro);
    }

    // Cenário 17: Registro de uma compra completa
    @Test
    public void testCenario17_RegistroCompraCompleta() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        
        boolean resultado = compraService.adicionarCompra(compra);
        
        assertTrue("Compra deve ser registrada com sucesso", resultado);
        assertEquals("Status deve ser PAGA", "PAGA", compra.getStatus());
        assertEquals("Total de compras deve ser 1", 1, compraService.getTotalCompras());
    }

    // Cenário 18: Compra para cliente inexistente
    @Test
    public void testCenario18_CompraClienteInexistente() throws Exception {
        Compra compra = new Compra("99999999999", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        
        try {
            compraService.adicionarCompra(compra);
            fail("Deve lançar exceção para cliente inexistente");
        } catch (Exception e) {
            assertEquals("Mensagem de erro deve indicar cliente não cadastrado", 
                        "Cliente não cadastrado", e.getMessage());
        }
    }

    // Cenário 19: Consultar relatório completo da compra
    @Test
    public void testCenario19_ConsultarRelatorioCompra() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compra);
        
        Compra relatorio = compraService.obterRelatorioCompra("12345678901", "Dom Casmurro");
        
        assertNotNull("Relatório deve ser encontrado", relatorio);
        assertEquals("Cliente deve ser João Silva (CPF)", "12345678901", relatorio.getCpfCliente());
        assertEquals("Livro deve ser Dom Casmurro", "Dom Casmurro", relatorio.getTituloLivro());
        assertEquals("Valor deve ser 30.00", 30.00, relatorio.getValorTotal(), 0.01);
        assertEquals("Forma de pagamento deve ser Cartão de Crédito", "Cartão de Crédito", 
                    relatorio.getFormaPagamento());
    }
}
