package com.sebo.persistence;

import com.sebo.models.Cliente;

import java.util.LinkedHashMap;
import java.util.Map;

/** Adaptador em memória para preservar o comportamento acadêmico e os testes existentes. */
public class InMemoryClienteRepository implements ClienteRepository {
    private final Map<String, Cliente> clientes = new LinkedHashMap<>();

    @Override
    public boolean existePorCpf(String cpfNormalizado) {
        return clientes.containsKey(cpfNormalizado);
    }

    @Override
    public Cliente buscarPorCpf(String cpfNormalizado) {
        return clientes.get(cpfNormalizado);
    }

    @Override
    public void inserir(String cpfNormalizado, Cliente cliente) {
        if (clientes.containsKey(cpfNormalizado)) {
            throw new ClienteDuplicadoException("CPF já existe", null);
        }
        clientes.put(cpfNormalizado, cliente);
    }

    @Override
    public boolean atualizar(String cpfNormalizado, Cliente cliente) {
        if (!clientes.containsKey(cpfNormalizado)) {
            return false;
        }
        clientes.put(cpfNormalizado, cliente);
        return true;
    }

    @Override
    public boolean remover(String cpfNormalizado) {
        return clientes.remove(cpfNormalizado) != null;
    }

    @Override
    public int contar() {
        return clientes.size();
    }
}
