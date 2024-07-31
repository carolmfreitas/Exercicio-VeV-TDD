package com.example.processador;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessadorDeContasTest {

    @Test
    public void testFaturaPagaComContasPagasEmDiaPorBoleto() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Carol");
        Conta conta1 = new Conta("001", LocalDate.of(2023, 2, 20), 500.00);
        Conta conta2 = new Conta("002", LocalDate.of(2023, 2, 20), 400.00);
        Conta conta3 = new Conta("003", LocalDate.of(2023, 2, 20), 600.00);
        List<Conta> contas = Arrays.asList(conta1, conta2, conta3);
        ProcessadorDeContas processador = new ProcessadorDeContas();

        String resultado = processador.processarFatura(fatura, contas);

        assertEquals("PAGA", resultado);
    }

    @Test
    public void testFaturaPagaComCartaoDeCreditoETransferencia() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Joao");
        Conta conta1 = new Conta("001", LocalDate.of(2023, 2, 5), 700.00);
        Conta conta2 = new Conta("002", LocalDate.of(2023, 2, 17), 800.00);
        List<Conta> contas = Arrays.asList(conta1, conta2);
        ProcessadorDeContas processador = new ProcessadorDeContas();

        String resultado = processador.processarFatura(fatura, contas);

        assertEquals("PAGA", resultado);
    }

    @Test
    public void testFaturaPendenteComCartaoDeCreditoAtrasado() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1500.00, "Maria");
        Conta conta1 = new Conta("001", LocalDate.of(2023, 2, 6), 700.00); // Cartão de crédito (atrasado)
        Conta conta2 = new Conta("002", LocalDate.of(2023, 2, 17), 800.00); // Transferência
        List<Conta> contas = Arrays.asList(conta1, conta2);
        ProcessadorDeContas processador = new ProcessadorDeContas();

        String resultado = processador.processarFatura(fatura, contas);

        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testPagamentoBoletoComAtraso() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Ana");
        Conta conta1 = new Conta("001", LocalDate.of(2023, 2, 15), 500.00);
        Conta conta2 = new Conta("002", LocalDate.of(2023, 2, 18), 600.00); // Pagamento em atraso
        List<Conta> contas = Arrays.asList(conta1, conta2);
        ProcessadorDeContas processador = new ProcessadorDeContas();

        String resultado = processador.processarFatura(fatura, contas);

        assertEquals("PAGA", resultado);
    }

    @Test
    public void testPagamentoBoletoForaDoLimite() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 5000.00, "Pedro");
        Conta conta1 = new Conta("001", LocalDate.of(2023, 2, 20), 6000.00); // Valor fora do limite
        List<Conta> contas = Arrays.asList(conta1);
        ProcessadorDeContas processador = new ProcessadorDeContas();

        String resultado = processador.processarFatura(fatura, contas);

        assertEquals("PENDENTE", resultado);
    }
}
