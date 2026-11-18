package com.sebo.models;

public class Livro {
    private String titulo;
    private int ano;
    private String autor;
    private String estado;
    private int codigoFuncionario;

    public Livro() {
    }

    public Livro(String titulo, int ano, String autor, String estado, int codigoFuncionario) {
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.estado = estado;
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(int codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", ano=" + ano +
                ", autor='" + autor + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoFuncionario=" + codigoFuncionario +
                '}';
    }
}
