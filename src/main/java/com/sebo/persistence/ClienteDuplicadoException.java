package com.sebo.persistence;

public class ClienteDuplicadoException extends PersistenceException {
    public ClienteDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
