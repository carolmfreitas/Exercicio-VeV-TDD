package com.example.processador;

import java.time.LocalDate;
import java.util.List;

public class ProcessadorDeContas {
    public String processarFatura(Fatura fatura, List<Conta> contas) {
        double valorTotalFatura = fatura.getValorTotal();
        double somaPagamentos = 0.0;

        for (Conta conta : contas) {
            Pagamento pagamento = criarPagamento(conta);

            if (verificarInclusaoPagamento(pagamento, fatura.getData())) {
                somaPagamentos += calcularValorPago(pagamento, conta.getData());
            }
        }

        return somaPagamentos >= valorTotalFatura ? "PAGA" : "PENDENTE";
    }

    private Pagamento criarPagamento(Conta conta) {
        double valorPago = conta.getValorPago();
        LocalDate dataPagamento = conta.getData();
        String tipoPagamento = conta.getTipoPagamento();

        return new Pagamento(valorPago, dataPagamento, tipoPagamento);
    }

    private boolean verificarInclusaoPagamento(Pagamento pagamento, LocalDate dataFatura) {
        String tipoPagamento = pagamento.getTipo();
        LocalDate dataPagamento = pagamento.getData();

        if (tipoPagamento.equals("BOLETO")) {
            return true;
        } 
        
        else if (tipoPagamento.equals("CARTAO_CREDITO")) {
            // Permitir pagamento apenas até 15 dias antes da data da fatura
            if(dataPagamento.plusDays(15).isAfter(dataFatura)){
                return false;
            } 
            else if(dataPagamento.plusDays(15).isEqual(dataFatura)){
                return true;
            }
            else{
                return true;
            }
        } 
        
        else if (tipoPagamento.equals("TRANSFERENCIA")) {
            if(dataPagamento.isEqual(dataFatura)) return true;
            else if (dataPagamento.isBefore(dataFatura)) return true;
        }

        return false;
    }

    private double calcularValorPago(Pagamento pagamento, LocalDate dataConta) {
        String tipoPagamento = pagamento.getTipo();
        double valorPago = pagamento.getValorPago();
        LocalDate dataPagamento = pagamento.getData();

        if (tipoPagamento.equals("BOLETO")) {
            
            if (valorPago < 0.01 || valorPago > 5000.00) {
                return 0.0;
            }
            
            if (dataPagamento.isAfter(dataConta)) {
                return valorPago * 1.10;
            }
        }

        return valorPago;
    }
}
