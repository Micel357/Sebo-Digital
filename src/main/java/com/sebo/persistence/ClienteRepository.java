package com.sebo.persistence;

import com.sebo.models.Cliente;

/** Operações de persistência usadas pelo serviço de clientes. */
public interface ClienteRepository {
    boolean existePorCpf(String cpfNormalizado);

    Cliente buscarPorCpf(String cpfNormalizado);

    void inserir(String cpfNormalizado, Cliente cliente);

    boolean atualizar(String cpfNormalizado, Cliente cliente);

    boolean remover(String cpfNormalizado);

    int contar();
}
