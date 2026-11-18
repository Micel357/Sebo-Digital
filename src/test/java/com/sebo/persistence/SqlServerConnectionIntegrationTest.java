package com.sebo.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Executa contra um SQL Server configurado apenas quando o sistema pede explicitamente. */
@EnabledIfSystemProperty(named = "sebo.sqlserver.integration", matches = "true")
class SqlServerConnectionIntegrationTest {
    @Test
    void connectsAndCanReadTheClientTable() throws Exception {
        SqlServerClienteRepository repository = new SqlServerClienteRepository(
                SqlServerConnectionFactory.fromEnvironment());

        // A consulta COUNT também confirma que o banco e o script 001_create_clientes.sql existem.
        assertTrue(repository.contar() >= 0);
    }
}
