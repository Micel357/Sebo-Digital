package com.sebo.service;

import com.sebo.models.Compra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompraService {
    private List<Compra> compras = new ArrayList<>();
    private ClienteService clienteService;
    private LivroService livroService;

    public CompraService(ClienteService clienteService, LivroService livroService) {
        this.clienteService = clienteService;
        this.livroService = livroService;
    }

    public boolean adicionarCompra(Compra compra) throws Exception {
        if (compra == null) {
            throw new Exception("Compra não pode ser nula");
        }

        // Verifica se cliente existe
        if (clienteService.buscarPorCPF(compra.getCpfCliente()) == null) {
            throw new Exception("Cliente não cadastrado");
        }

        // Verifica se livro existe
        if (livroService.buscarPorTitulo(compra.getTituloLivro()) == null) {
            throw new Exception("Livro não encontrado");
        }

        compra.setStatus("PAGA");
        compras.add(compra);
        return true;
    }

    public List<Compra> buscarCompraPorCPF(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("CPF não pode ser vazio");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cpf);
        List<Compra> resultado = new ArrayList<>();

        for (Compra compra : compras) {
            if (ValidadorCPF.normalizarCPF(compra.getCpfCliente()).equals(cpfNormalizado)) {
                resultado.add(compra);
            }
        }

        return resultado;
    }

    public Compra obterRelatorioCompra(String cpf, String tituloLivro) throws Exception {
        List<Compra> comprasCliente = buscarCompraPorCPF(cpf);

        for (Compra compra : comprasCliente) {
            if (compra.getTituloLivro().equals(tituloLivro)) {
                return compra;
            }
        }

        return null;
    }

    public int getTotalCompras() {
        return compras.size();
    }

    public void limpar() {
        compras.clear();
    }
}
