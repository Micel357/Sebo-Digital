package com.sebo.persistence;

import com.sebo.models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlServerClienteRepository implements ClienteRepository {
    private static final String SQL_EXISTS =
            "SELECT 1 FROM dbo.Clientes WHERE cpf = ?";
    private static final String SQL_FIND =
            "SELECT cpf, nome, email, telefone FROM dbo.Clientes WHERE cpf = ?";
    private static final String SQL_INSERT =
            "INSERT INTO dbo.Clientes (cpf, nome, email, telefone) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE dbo.Clientes SET nome = ?, email = ?, telefone = ? WHERE cpf = ?";
    private static final String SQL_DELETE =
            "DELETE FROM dbo.Clientes WHERE cpf = ?";
    private static final String SQL_COUNT =
            "SELECT COUNT(*) FROM dbo.Clientes";

    private final ConnectionFactory connectionFactory;

    public SqlServerClienteRepository(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            throw new IllegalArgumentException("A fábrica de conexões é obrigatória");
        }
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean existePorCpf(String cpfNormalizado) {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EXISTS)) {
            statement.setString(1, cpfNormalizado);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw persistenceFailure("consultar cliente", exception);
        }
    }

    @Override
    public Cliente buscarPorCpf(String cpfNormalizado) {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND)) {
            statement.setString(1, cpfNormalizado);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return new Cliente(
                        resultSet.getString("nome"),
                        resultSet.getString("cpf"),
                        resultSet.getString("email"),
                        resultSet.getString("telefone"));
            }
        } catch (SQLException exception) {
            throw persistenceFailure("buscar cliente", exception);
        }
    }

    @Override
    public void inserir(String cpfNormalizado, Cliente cliente) {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, cpfNormalizado);
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getTelefone());
            statement.executeUpdate();
        } catch (SQLException exception) {
            if (exception.getErrorCode() == 2601 || exception.getErrorCode() == 2627) {
                throw new ClienteDuplicadoException("CPF já existe", exception);
            }
            throw persistenceFailure("inserir cliente", exception);
        }
    }

    @Override
    public boolean atualizar(String cpfNormalizado, Cliente cliente) {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cpfNormalizado);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw persistenceFailure("atualizar cliente", exception);
        }
    }

    @Override
    public boolean remover(String cpfNormalizado) {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, cpfNormalizado);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw persistenceFailure("remover cliente", exception);
        }
    }

    @Override
    public int contar() {
        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException exception) {
            throw persistenceFailure("contar clientes", exception);
        }
    }

    private static PersistenceException persistenceFailure(String operation, SQLException cause) {
        return new PersistenceException("Falha ao " + operation + " no SQL Server.", cause);
    }
}
