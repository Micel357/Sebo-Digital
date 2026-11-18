package com.sebo.models;

import java.time.LocalDateTime;

public class Compra {
    private String cpfCliente;
    private String tituloLivro;
    private int quantidade;
    private double valorTotal;
    private String formaPagamento;
    private LocalDateTime dataHora;
    private String status;

    public Compra() {
    }

    public Compra(String cpfCliente, String tituloLivro, int quantidade, double valorTotal, 
                  String formaPagamento, LocalDateTime dataHora, String status) {
        this.cpfCliente = cpfCliente;
        this.tituloLivro = tituloLivro;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.dataHora = dataHora;
        this.status = status;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "cpfCliente='" + cpfCliente + '\'' +
                ", tituloLivro='" + tituloLivro + '\'' +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                '}';
    }
}
