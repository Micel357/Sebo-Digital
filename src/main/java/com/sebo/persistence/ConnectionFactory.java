package com.sebo.persistence;

import java.sql.Connection;
import java.sql.SQLException;

/** Abre conexões para que os repositórios possam ser testados sem um servidor real. */
@FunctionalInterface
public interface ConnectionFactory {
    Connection openConnection() throws SQLException;
}
