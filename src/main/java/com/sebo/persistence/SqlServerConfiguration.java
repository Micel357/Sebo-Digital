package com.sebo.persistence;

import com.sebo.service.ClienteService;

/** Ponto simples de composição para ativar a persistência SQL Server sem Spring. */
public final class SqlServerConfiguration {
    private SqlServerConfiguration() {
    }

    public static ClienteService clienteServiceFromEnvironment() {
        return new ClienteService(new SqlServerClienteRepository(
                SqlServerConnectionFactory.fromEnvironment()));
    }
}
