package com.example.processador;

import java.time.LocalDate;

public class Pagamento {
    private double valorPago;
    private LocalDate data;
    private String tipo;

    public Pagamento(double valorPago, LocalDate data, String tipo) {
        this.valorPago = valorPago;
        this.data = data;
        this.tipo = tipo;
    }

    public double getValorPago() {
        return valorPago;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "valorPago=" + valorPago +
                ", data=" + data +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
