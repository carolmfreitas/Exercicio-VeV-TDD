package com.example.processador;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PagamentoTest {

    @Test
    void testPagamentoConstrutor() {
        LocalDate dataPagamento = LocalDate.of(2024, 7, 31);
        Pagamento pagamento = new Pagamento(100.00, dataPagamento, "CARTAO_CREDITO");

        assertEquals(100.00, pagamento.getValorPago());
        assertEquals(dataPagamento, pagamento.getData());
        assertEquals("CARTAO_CREDITO", pagamento.getTipo());
    }

    @Test
    void testPagamentoGetters() {
        LocalDate dataPagamento = LocalDate.of(2024, 8, 1);
        Pagamento pagamento = new Pagamento(200.00, dataPagamento, "BOLETO");

        assertEquals(200.00, pagamento.getValorPago());
        assertEquals(dataPagamento, pagamento.getData());
        assertEquals("BOLETO", pagamento.getTipo());
    }
}
