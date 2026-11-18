package com.sebo.service;

public class ValidadorCPF {

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }

        // Remove caracteres especiais
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se não é sequência repetida
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return true;
    }

    public static String normalizarCPF(String cpf) {
        if (cpf == null) {
            return null;
        }
        // Remove pontuação e retorna apenas números
        return cpf.replaceAll("[^0-9]", "");
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
}
