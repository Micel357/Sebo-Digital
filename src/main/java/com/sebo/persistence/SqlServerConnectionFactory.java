package com.sebo.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/** Cria conexões JDBC usando configuração externa, sem armazenar credenciais no código. */
public class SqlServerConnectionFactory implements ConnectionFactory {
    public static final String URL_ENV = "SEBO_DB_URL";
    public static final String USER_ENV = "SEBO_DB_USER";
    public static final String PASSWORD_ENV = "SEBO_DB_PASSWORD";

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public SqlServerConnectionFactory(String jdbcUrl, String username, String password) {
        this.jdbcUrl = requireValue(jdbcUrl, URL_ENV);
        if (!this.jdbcUrl.startsWith("jdbc:sqlserver://")) {
            throw new IllegalArgumentException(URL_ENV + " deve começar com jdbc:sqlserver://");
        }
        this.username = requireValue(username, USER_ENV);
        this.password = requireValue(password, PASSWORD_ENV);
    }

    public static SqlServerConnectionFactory fromEnvironment() {
        return fromEnvironment(System.getenv());
    }

    public static SqlServerConnectionFactory fromEnvironment(Map<String, String> environment) {
        if (environment == null) {
            throw new IllegalArgumentException("As variáveis de ambiente são obrigatórias");
        }
        return new SqlServerConnectionFactory(
                environment.get(URL_ENV),
                environment.get(USER_ENV),
                environment.get(PASSWORD_ENV));
    }

    @Override
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private static String requireValue(String value, String variableName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Configure a variável de ambiente " + variableName);
        }
        return value.trim();
    }
}
