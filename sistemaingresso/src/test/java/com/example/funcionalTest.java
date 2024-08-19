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

    /**
     * Testa a proporção de ingressos e a geração de relatórios.
     * - Verifica se as proporções de ingressos são válidas.
     * - Gera relatórios e verifica o estado financeiro.
     */
    @Test
    public void testTicketProportionsWithValidDiscount() {
        TicketBatch batch = new TicketBatch(1, 100, 0.10);
        int normalCount = 0;
        int meiaEntradaCount = 0;
        int vipCount = 0;

        for (Ticket ticket : batch.getTickets()) {
            switch (ticket.getType()) {
                case NORMAL:
                    normalCount++;
                    break;
                case MEIA_ENTRADA:
                    meiaEntradaCount++;
                    break;
                case VIP:
                    vipCount++;
                    break;
            }
        }

        assertTrue(vipCount >= 20 && vipCount <= 30);
        assertEquals(10, meiaEntradaCount);
        assertTrue(normalCount >= 60 && normalCount <= 70);
    }

    @Test
    public void testGenerateReportWithProfit() {
        Show show = new Show("2024-08-19", "Artista X", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.10);
        show.addTicketBatch(batch);
        batch.getTickets().forEach(ticket -> ticket.setSold(true));

        String report = show.generateReport();
        assertTrue(report.contains("Show Report:"));
        assertTrue(report.contains("Artist: Artista X"));
        assertTrue(report.contains("Date: 2024-08-19"));
        assertTrue(report.contains("VIP Tickets Sold:"));
        assertTrue(report.contains("Meia Entrada Tickets Sold:"));
        assertTrue(report.contains("Normal Tickets Sold:"));
        assertTrue(report.contains("Net Revenue:"));
        assertTrue(report.contains("Financial Status: LUCRO"));
    }

    @Test
    public void testGenerateReportWithLoss() {
        Show show = new Show("2024-08-19", "Artista Y", 5000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.10);
        show.addTicketBatch(batch);
        batch.getTickets().forEach(ticket -> ticket.setSold(true));

        String report = show.generateReport();
        assertTrue(report.contains("Show Report:"));
        assertTrue(report.contains("Artist: Artista Y"));
        assertTrue(report.contains("Date: 2024-08-19"));
        assertTrue(report.contains("VIP Tickets Sold:"));
        assertTrue(report.contains("Meia Entrada Tickets Sold:"));
        assertTrue(report.contains("Normal Tickets Sold:"));
        assertTrue(report.contains("Net Revenue:"));
        assertTrue(report.contains("Financial Status: PREJUÍZO"));
    }
}
