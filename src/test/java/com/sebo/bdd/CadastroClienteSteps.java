package com.sebo.bdd;

import com.sebo.models.Cliente;
import com.sebo.service.ClienteService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CadastroClienteSteps {
    private ClienteService clienteService;
    private Cliente cliente;
    private boolean cadastrado;

    @Dado("que tenho um cliente válido sem telefone")
    public void tenhoUmClienteValidoSemTelefone() {
        clienteService = new ClienteService();
        cliente = new Cliente("Ana Souza", "52998224725", "ana@example.com", null);
    }

    @Quando("realizo o cadastro")
    public void realizoOCadastro() throws Exception {
        cadastrado = clienteService.cadastrarCliente(cliente);
    }

    @Entao("o cliente deve ser cadastrado")
    public void clienteDeveSerCadastrado() {
        assertTrue(cadastrado);
    }

    @Entao("o telefone padrão deve ser aplicado")
    public void telefonePadraoDeveSerAplicado() {
        assertEquals("81900000000", cliente.getTelefone());
    }
}
