package com.example.processador;

import java.time.LocalDate;

public class Fatura {
     private LocalDate data;
    private double valorTotal;
    private String nomeCliente;

    public Fatura(LocalDate data, double valorTotal, String nomeCliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.nomeCliente = nomeCliente;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public LocalDate getData(){
        return this.data;
    }

    public String getNomeCliente(){
        return this.nomeCliente;
    }

}
