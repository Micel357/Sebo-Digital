package com.sebo.service;

import com.sebo.models.Livro;
import java.util.HashMap;
import java.util.Map;

public class LivroService {
    private Map<String, Livro> livros = new HashMap<>();
    private FuncionarioService funcionarioService;

    public LivroService(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public boolean adicionarLivro(Livro livro) throws Exception {
        if (livro == null) {
            throw new Exception("Livro não pode ser nulo");
        }

        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Exception("Título é obrigatório");
        }

        if (livro.getCodigoFuncionario() == 0) {
            throw new Exception("Código do funcionário é obrigatório");
        }

        if (!funcionarioService.existeFuncionario(livro.getCodigoFuncionario())) {
            throw new Exception("Funcionário não encontrado");
        }

        livros.put(livro.getTitulo(), livro);
        return true;
    }

    public Livro buscarPorTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("Título não pode ser vazio");
        }

        return livros.get(titulo);
    }

    public boolean atualizarEstado(String titulo, String novoEstado) throws Exception {
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("Título não pode ser vazio");
        }

        Livro livro = livros.get(titulo);
        if (livro == null) {
            throw new Exception("Livro não encontrado");
        }

        livro.setEstado(novoEstado);
        return true;
    }

    public int getTotalLivros() {
        return livros.size();
    }

    public void limpar() {
        livros.clear();
    }
}
