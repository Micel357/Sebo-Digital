package com.sebo.service;

import com.sebo.models.Cliente;
import java.util.HashMap;
import java.util.Map;

public class ClienteService {
    private Map<String, Cliente> clientes = new HashMap<>();

    public boolean cadastrarCliente(Cliente cliente) throws Exception {
        if (cliente == null) {
            throw new Exception("Cliente não pode ser nulo");
        }

        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new Exception("Nome é obrigatório");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new Exception("CPF é obrigatório");
        }

        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new Exception("E-mail é obrigatório");
        }

        if (!ValidadorCPF.validarCPF(cliente.getCpf())) {
            throw new Exception("O CPF deve conter exatamente 11 dígitos");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cliente.getCpf());

        if (clientes.containsKey(cpfNormalizado)) {
            throw new Exception("CPF já existe");
        }

        // Define telefone padrão se não informado
        if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()) {
            cliente.setTelefone("81900000000");
        }

        clientes.put(cpfNormalizado, cliente);
        return true;
    }

    public Cliente buscarPorCPF(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("CPF não pode ser vazio");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cpf);
        return clientes.get(cpfNormalizado);
    }

    public boolean atualizarCliente(String cpf, Cliente clienteAtualizado) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("CPF não pode ser vazio");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cpf);

        if (!clientes.containsKey(cpfNormalizado)) {
            throw new Exception("Cliente não encontrado");
        }

        Cliente cliente = clientes.get(cpfNormalizado);
        if (clienteAtualizado.getNome() != null) {
            cliente.setNome(clienteAtualizado.getNome());
        }
        if (clienteAtualizado.getEmail() != null) {
            cliente.setEmail(clienteAtualizado.getEmail());
        }
        if (clienteAtualizado.getTelefone() != null) {
            cliente.setTelefone(clienteAtualizado.getTelefone());
        }

        return true;
    }

    public boolean removerCliente(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("CPF não pode ser vazio");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cpf);

        if (!clientes.containsKey(cpfNormalizado)) {
            throw new Exception("Cliente não encontrado");
        }

        clientes.remove(cpfNormalizado);
        return true;
    }

    public int getTotalClientes() {
        return clientes.size();
    }

    public void limpar() {
        clientes.clear();
    }
}
