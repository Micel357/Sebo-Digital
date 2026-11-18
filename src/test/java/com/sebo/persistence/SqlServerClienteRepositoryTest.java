package com.sebo.persistence;

import com.sebo.models.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SqlServerClienteRepositoryTest {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private PreparedStatement statement;
    private SqlServerClienteRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connectionFactory = mock(ConnectionFactory.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        when(connectionFactory.openConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        repository = new SqlServerClienteRepository(connectionFactory);
    }

    @Test
    void insertsClienteWithPreparedStatementParameters() throws SQLException {
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@example.com", "81900000000");
        when(statement.executeUpdate()).thenReturn(1);

        repository.inserir("12345678901", cliente);

        verify(statement).setString(1, "12345678901");
        verify(statement).setString(2, "João Silva");
        verify(statement).setString(3, "joao@example.com");
        verify(statement).setString(4, "81900000000");
        verify(statement).executeUpdate();
        verify(statement).close();
        verify(connection).close();
    }

    @Test
    void findsClienteAndReturnsNullWhenNotFound() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("nome")).thenReturn("João Silva");
        when(resultSet.getString("cpf")).thenReturn("12345678901");
        when(resultSet.getString("email")).thenReturn("joao@example.com");
        when(resultSet.getString("telefone")).thenReturn("81900000000");

        Cliente cliente = repository.buscarPorCpf("12345678901");

        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        verify(statement).setString(1, "12345678901");
        verify(resultSet).close();
        verify(statement).close();
        verify(connection).close();

        when(connectionFactory.openConnection()).thenReturn(connection);
        when(resultSet.next()).thenReturn(false);
        assertNull(repository.buscarPorCpf("00000000000"));
    }

    @Test
    void updatesAndRemovesByNormalizedCpf() throws SQLException {
        when(statement.executeUpdate()).thenReturn(1);
        Cliente cliente = new Cliente("João Silva", "12345678901", "novo@example.com", "81900000000");

        assertTrue(repository.atualizar("12345678901", cliente));
        verify(statement).setString(4, "12345678901");

        assertTrue(repository.remover("12345678901"));
        verify(statement).setString(1, "12345678901");
    }

    @Test
    void reportsDuplicateCpfAndWrapsDatabaseFailures() throws SQLException {
        when(statement.executeUpdate()).thenThrow(new SQLException("duplicate", "23000", 2627));
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@example.com", "81900000000");

        assertThrows(ClienteDuplicadoException.class,
                () -> repository.inserir("12345678901", cliente));

        when(connectionFactory.openConnection()).thenThrow(new SQLException("offline"));
        assertThrows(PersistenceException.class, () -> repository.contar());
    }

    @Test
    void returnsCountAndFalseWhenDeleteAffectsNoRows() throws SQLException {
        when(statement.executeUpdate()).thenReturn(0);
        assertFalse(repository.remover("00000000000"));

        ResultSet resultSet = mock(ResultSet.class);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(7);
        assertEquals(7, repository.contar());
    }
}
