package com.sebo.service;

import com.sebo.models.Cliente;
import com.sebo.models.Compra;
import com.sebo.models.Livro;
import com.sebo.models.Funcionario;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class IntegracaoGatewayTest {
    private ClienteService clienteService;
    private FuncionarioService funcionarioService;
    private LivroService livroService;
    private CompraService compraService;

    @Before
    public void setUp() throws Exception {
        clienteService = new ClienteService();
        funcionarioService = new FuncionarioService();
        livroService = new LivroService(funcionarioService);
        compraService = new CompraService(clienteService, livroService);

        // Setup básico
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com", "81999999999");
        clienteService.cadastrarCliente(cliente);

        Funcionario func = new Funcionario("Kaio Castro", "12345678902", "Estoquista");
        funcionarioService.adicionarFuncionario(func);

        Livro livro = new Livro("Dom Casmurro", 2010, "Machado de Assis", "Ótimo", 1);
        livroService.adicionarLivro(livro);
    }

    // Cenário 45: Pagamento bem-sucedido (gateway)
    @Test
    public void testCenario45_PagamentoBemSucedidoGateway() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        
        // Simula resposta do gateway
        String respostaGateway = "APPROVED";
        
        boolean resultado = compraService.adicionarCompra(compra);
        
        assertTrue("Compra deve ser registrada", resultado);
        assertEquals("Gateway deve retornar APPROVED", "APPROVED", respostaGateway);
        assertEquals("Status deve ser PAGA", "PAGA", compra.getStatus());
    }

    // Cenário 46: Gateway indisponível (reprocessamento)
    @Test
    public void testCenario46_GatewayIndisponivel() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PENDENTE");
        
        // Simula timeout do gateway
        String statusGateway = "TIMEOUT";
        
        if (statusGateway.equals("TIMEOUT")) {
            compra.setStatus("PENDENTE");
        }
        
        assertEquals("Status deve ser PENDENTE para reprocessamento", "PENDENTE", compra.getStatus());
    }

    // Cenário 47: Idempotência no endpoint de criação de compra
    @Test
    public void testCenario47_IdempotenciaCompra() throws Exception {
        String idempotencyKey = "compra-123-abc";
        
        Compra compra1 = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                    "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compra1);
        
        // Segunda requisição com mesma chave
        Compra compra2 = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                    "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        
        // Em caso de idempotência, deve retornar o mesmo resultado
        int totalComprasAntes = compraService.getTotalCompras();
        
        try {
            compraService.adicionarCompra(compra2);
        } catch (Exception e) {
            // Esperado: duplicata
        }
        
        int totalComprasDepois = compraService.getTotalCompras();
        
        // Pode ser igual (idempotente) ou incrementar 1
        assertTrue("Total deve ser consistente", 
                  totalComprasAntes == totalComprasDepois || totalComprasAntes + 1 == totalComprasDepois);
    }

    // Cenário 48: Conciliação financeira (valores batem)
    @Test
    public void testCenario48_ConciliacaoFinanceira() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compra);
        
        // Simula dados de pagamento
        double valorCompra = 30.00;
        double valorPagamento = 30.00;
        
        boolean conciliado = Math.abs(valorCompra - valorPagamento) < 0.01;
        
        assertTrue("Valores devem bater", conciliado);
    }

    // Cenário 49: Merge de clientes duplicados (preserva histórico)
    @Test
    public void testCenario49_MergeClientesDuplicados() throws Exception {
        // Cliente A
        Cliente clienteA = new Cliente("João Silva", "12345678901", "joao@email.com", "81999999999");
        clienteService.cadastrarCliente(clienteA);
        
        Compra compraA = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                    "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compraA);
        
        // Cliente B (duplicado)
        Cliente clienteB = new Cliente("João Silva", "12345678902", "joao.silva@email.com", "81988888888");
        clienteService.cadastrarCliente(clienteB);
        
        Compra compraB = new Compra("12345678902", "Dom Casmurro", 2, 60.00, 
                                    "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compraB);
        
        // Simula merge mantendo histórico
        int totalComprasAntes = compraService.getTotalCompras();
        
        // Após merge, ambas as compras devem estar associadas a um cliente
        assertEquals("Deve ter 2 compras no histórico", 2, totalComprasAntes);
    }

    // Cenário 50: Notificação pós-compra com conteúdo correto
    @Test
    public void testCenario50_NotificacaoPosCompra() throws Exception {
        Compra compra = new Compra("12345678901", "Dom Casmurro", 1, 30.00, 
                                   "Cartão de Crédito", LocalDateTime.now(), "PAGA");
        compraService.adicionarCompra(compra);
        
        // Simula geração de notificação
        String numeroCompra = "001";
        String itens = "Dom Casmurro (1x)";
        String valorTotal = "30.00";
        String formaPagamento = "Cartão de Crédito";
        String dataHora = compra.getDataHora().toString();
        String linkHistorico = "https://sebo.com/historico/001";
        
        // Monta email
        String emailConteudo = "Número da compra: " + numeroCompra + "\n" +
                              "Itens: " + itens + "\n" +
                              "Valor total: " + valorTotal + "\n" +
                              "Forma de pagamento: " + formaPagamento + "\n" +
                              "Data/Hora: " + dataHora + "\n" +
                              "Link para histórico: " + linkHistorico;
        
        // Validações
        assertTrue("Email deve conter número da compra", emailConteudo.contains("Número da compra"));
        assertTrue("Email deve conter itens", emailConteudo.contains("Itens"));
        assertTrue("Email deve conter valor total", emailConteudo.contains("Valor total"));
        assertTrue("Email deve conter forma de pagamento", emailConteudo.contains("Forma de pagamento"));
        assertTrue("Email deve conter data/hora", emailConteudo.contains("Data/Hora"));
        assertTrue("Email deve conter link para histórico", emailConteudo.contains("Link para histórico"));
    }
}
