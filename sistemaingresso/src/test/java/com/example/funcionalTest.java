package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes funcionais para o sistema de venda de ingressos.
 *
 * Esta classe de teste cobre diferentes técnicas de teste para a aplicação:
 * 1. Análise de Valores Limites
 * 2. Partições de Equivalência
 * 3. Tabelas de Decisão
 */
public class funcionalTest {

    // ====================================
    // Análise de Valores Limites
    // ====================================

    /**
     * Testa limites de valores para TicketBatch.
     * - Número mínimo e máximo de ingressos.
     * - Valor mínimo e máximo de desconto.
     */
    @Test
    public void testMinimumNumberOfTickets() {
        TicketBatch batch = new TicketBatch(1, 1, 0.10);
        assertEquals(1, batch.getTickets().size());
    }

    @Test
    public void testMaximumNumberOfTickets() {
        int maxTickets = 10000; // Exemplo de valor máximo suportado
        TicketBatch batch = new TicketBatch(1, maxTickets, 0.10);
        assertEquals(maxTickets, batch.getTickets().size());
    }

    @Test
    public void testMinimumDiscount() {
        TicketBatch batch = new TicketBatch(1, 100, 0.00);
        assertEquals(0.00, batch.getDiscount(), 0.01);
    }

    @Test
    public void testMaximumDiscount() {
        TicketBatch batch = new TicketBatch(1, 100, 0.25);
        assertEquals(0.25, batch.getDiscount(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiscountAboveMaximum() {
        new TicketBatch(1, 100, 0.26); // Deve lançar exceção por desconto inválido
    }

    @Test
    public void testCalculateNetRevenueWithoutSpecialDate() {
        Show show = new Show("2024-08-19", "Artista X", 1000.00, 2000.00, false);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        show.addTicketBatch(batch);
        batch.getTickets().forEach(ticket -> ticket.setSold(true));

        double expectedRevenue = 500 * (10 + 15) - 2000.00; // Receita líquida sem custo extra
        assertEquals(expectedRevenue, show.calculateNetRevenue(), 0.01);
    }

    @Test
    public void testCalculateNetRevenueWithSpecialDate() {
        Show show = new Show("2024-08-19", "Artista X", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        show.addTicketBatch(batch);
        batch.getTickets().forEach(ticket -> ticket.setSold(true));

        double expectedRevenue = 500 * (10 + 15) - 2000.00 * 1.15; // Receita líquida com custo extra
        assertEquals(expectedRevenue, show.calculateNetRevenue(), 0.01);
    }

    @Test
    public void testCalculateNetRevenueHighFee() {
        Show show = new Show("2024-08-19", "Artista X", 5000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.10);
        show.addTicketBatch(batch);
        batch.getTickets().forEach(ticket -> ticket.setSold(true));

        double expectedRevenue = 500 * 10 - (2000.00 * 1.15 + 5000.00); // Receita líquida negativa
        assertEquals(expectedRevenue, show.calculateNetRevenue(), 0.01);
    }

    // ====================================
    // Partições de Equivalência
    // ====================================

    /**
     * Testa valores válidos e inválidos para TicketType.
     * - Verifica se tipos válidos são reconhecidos.
     * - Verifica se tipos inválidos lançam exceções.
     */
    @Test
    public void testValidTicketTypes() {
        assertEquals(TicketType.NORMAL, TicketType.valueOf("NORMAL"));
        assertEquals(TicketType.MEIA_ENTRADA, TicketType.valueOf("MEIA_ENTRADA"));
        assertEquals(TicketType.VIP, TicketType.valueOf("VIP"));
    }

    @Test
    public void testInvalidTicketType() {
        try {
            TicketType.valueOf("INVALID_TYPE");
            fail("Deveria ter lançado uma exceção.");
        } catch (IllegalArgumentException e) {
            // Espera-se a exceção aqui
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDiscount() {
        new TicketBatch(1, 100, 0.30); // Deve lançar exceção por desconto inválido
    }

    // ====================================
    // Tabelas de Decisão
    // ====================================

    @Test
    void caso1_lucroEsperado() {
        Show show = new Show("Artista A", 1000.00, 2000.00, false, 500);
        show.aplicarDesconto(0.10);
        assertEquals(StatusFinanceiro.LUCRO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso2_lucroEsperadoComDesconto15() {
        Show show = new Show("Artista A", 1000.00, 2000.00, false, 500);
        show.aplicarDesconto(0.15);
        assertEquals(StatusFinanceiro.LUCRO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso3_lucroEsperadoComDesconto25() {
        Show show = new Show("Artista A", 1000.00, 2000.00, false, 500);
        show.aplicarDesconto(0.25);
        assertEquals(StatusFinanceiro.LUCRO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso4_lucroEsperadoDataEspecial() {
        Show show = new Show("Artista A", 1000.00, 2000.00, true, 500);
        show.aplicarDesconto(0.10);
        assertEquals(StatusFinanceiro.LUCRO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso5_prejuizoEsperadoCustoAlto() {
        Show show = new Show("Artista B", 5000.00, 2000.00, true, 500);
        show.aplicarDesconto(0.10);
        assertEquals(StatusFinanceiro.PREJUIZO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso6_prejuizoEsperadoComDesconto15() {
        Show show = new Show("Artista B", 5000.00, 2000.00, true, 500);
        show.aplicarDesconto(0.15);
        assertEquals(StatusFinanceiro.PREJUIZO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso7_prejuizoEsperadoComDesconto25() {
        Show show = new Show("Artista B", 5000.00, 2000.00, true, 500);
        show.aplicarDesconto(0.25);
        assertEquals(StatusFinanceiro.PREJUIZO, show.gerarRelatorioFinanceiro());
    }

    @Test
    void caso8_excecaoParaDescontoAcimaDoLimite() {
        Show show = new Show("Artista B", 5000.00, 2000.00, true, 500);
        assertThrows(IllegalArgumentException.class, () -> {
            show.aplicarDesconto(0.30); // Desconto acima do limite permitido
        });
    }

    @Test
    void caso8_excecaoParaTipoIngressoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ingresso("INVALIDO", 100.00, false);
        });
    }
}
