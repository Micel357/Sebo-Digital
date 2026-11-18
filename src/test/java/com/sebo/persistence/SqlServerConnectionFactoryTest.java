package com.sebo.persistence;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SqlServerConnectionFactoryTest {
    @Test
    void createsFactoryFromEnvironmentValues() {
        Map<String, String> environment = new HashMap<>();
        environment.put("SEBO_DB_URL", "jdbc:sqlserver://localhost:1433;databaseName=SeboDigital");
        environment.put("SEBO_DB_USER", "sa");
        environment.put("SEBO_DB_PASSWORD", "secret");

        SqlServerConnectionFactory factory = SqlServerConnectionFactory.fromEnvironment(environment);

        assertEquals("jdbc:sqlserver://localhost:1433;databaseName=SeboDigital", factory.getJdbcUrl());
    }

    @Test
    void requiresSqlServerUrlAndAllCredentials() {
        Map<String, String> environment = new HashMap<>();
        environment.put("SEBO_DB_URL", "jdbc:postgresql://localhost/test");
        environment.put("SEBO_DB_USER", "sa");
        environment.put("SEBO_DB_PASSWORD", "secret");

        assertThrows(IllegalArgumentException.class,
                () -> SqlServerConnectionFactory.fromEnvironment(environment));

        environment.put("SEBO_DB_URL", "jdbc:sqlserver://localhost:1433");
        environment.remove("SEBO_DB_PASSWORD");
        assertThrows(IllegalArgumentException.class,
                () -> SqlServerConnectionFactory.fromEnvironment(environment));
    }
}
