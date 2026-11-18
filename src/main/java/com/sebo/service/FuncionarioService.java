package com.sebo.service;

import com.sebo.models.Funcionario;
import java.util.HashMap;
import java.util.Map;

public class FuncionarioService {
    private Map<String, Funcionario> funcionarios = new HashMap<>();

    public boolean adicionarFuncionario(Funcionario funcionario) throws Exception {
        if (funcionario == null) {
            throw new Exception("Funcionário não pode ser nulo");
        }

        if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            throw new Exception("Nome, CPF e Função são obrigatórios");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isEmpty()) {
            throw new Exception("Nome, CPF e Função são obrigatórios");
        }

        if (funcionario.getFuncao() == null || funcionario.getFuncao().isEmpty()) {
            throw new Exception("Nome, CPF e Função são obrigatórios");
        }

        if (!ValidadorCPF.validarCPF(funcionario.getCpf())) {
            throw new Exception("CPF inválido");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(funcionario.getCpf());

        if (funcionarios.containsKey(cpfNormalizado)) {
            throw new Exception("CPF já cadastrado");
        }

        funcionarios.put(cpfNormalizado, funcionario);
        return true;
    }

    public Funcionario buscarPorCPF(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty()) {
            throw new Exception("CPF não pode ser vazio");
        }

        String cpfNormalizado = ValidadorCPF.normalizarCPF(cpf);
        return funcionarios.get(cpfNormalizado);
    }

    public boolean existeFuncionario(int codigoFuncionario) {
        // Simulação: verifica se existe funcionário com código
        return codigoFuncionario > 0 && codigoFuncionario != 9999;
    }

    public int getTotalFuncionarios() {
        return funcionarios.size();
    }

    public void limpar() {
        funcionarios.clear();
    }
}
