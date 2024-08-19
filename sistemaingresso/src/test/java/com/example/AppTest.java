
package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AppTest {

    @Test
    public void testCreateTicket() {
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        Ticket ticket = new Ticket(1, TicketType.NORMAL, batch);
        assertEquals(1, ticket.getId());
        assertEquals(TicketType.NORMAL, ticket.getType());
        assertFalse(ticket.isSold());
    }

    @Test
    public void testMarkTicketAsSold() {
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        Ticket ticket = new Ticket(1, TicketType.NORMAL, batch);
        assertFalse(ticket.isSold());
        ticket.setSold(true);
        assertTrue(ticket.isSold());
    }

    @Test
    public void testCreateTicketBatch() {
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        assertEquals(1, batch.getId());
        assertEquals(100, batch.getTicketCount());
        assertEquals(0.15, batch.getDiscount(), 0.0001);
        assertEquals(100, batch.getTicketCount());
    }

    @Test
    public void testTicketTypeProportion() {
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        int vipCount = 0;
        int meiaEntradaCount = 0;
        int normalCount = 0;
        for (Ticket ticket : batch.getTickets()) {
            switch (ticket.getType()) {
                case VIP:
                    vipCount++;
                    break;
                case MEIA_ENTRADA:
                    meiaEntradaCount++;
                    break;
                case NORMAL:
                    normalCount++;
                    break;
            }
        }
        assertTrue(vipCount >= 20 && vipCount <= 30);
        assertEquals(10, meiaEntradaCount);
        assertEquals(100 - vipCount - meiaEntradaCount, normalCount);
    }

    @Test
    public void testAddTicketBatch() {
        Show show = new Show("2024-11-30", "Davvi Duarte", 2500.0, 5000.0, true);
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        show.addTicketBatch(batch);
        assertEquals(1, show.getTicketBatches().size());
        assertEquals(batch, show.getTicketBatches().get(0));
    }

    @Test
    public void testCalculateNetRevenue() {
        Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        for (Ticket ticket : batch.getTickets()) {
            ticket.setSold(true);
        }
        show.addTicketBatch(batch);
        int totalTickets = 500;
        int vipTickets = (int) (totalTickets * 0.20);
        int meiaEntradaTickets = (int) (totalTickets * 0.10);
        int normalTickets = totalTickets - vipTickets - meiaEntradaTickets;
        double vipPrice = 20.00;
        double meiaEntradaPrice = 5.00;
        double normalPrice = 10.00;
        double expectedGrossRevenue = (vipTickets * vipPrice * (1 - 0.15)) +
                (meiaEntradaTickets * meiaEntradaPrice) +
                (normalTickets * normalPrice * (1 - 0.15));
        double expectedTotalCost = 2000.00 * 1.15 + 1000.00;
        double expectedNetRevenue = expectedGrossRevenue - expectedTotalCost;
        assertEquals(expectedNetRevenue, show.calculateNetRevenue(), 0.01);
    }

    @Test
    public void testReportGeneration() {
        Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        for (Ticket ticket : batch.getTickets()) {
            ticket.setSold(true);
        }
        show.addTicketBatch(batch);
        int totalTickets = 500;
        int vipTickets = (int) (totalTickets * 0.20);
        int meiaEntradaTickets = (int) (totalTickets * 0.10);
        int normalTickets = totalTickets - vipTickets - meiaEntradaTickets;
        double vipPrice = 20.00;
        double meiaEntradaPrice = 5.00;
        double normalPrice = 10.00;
        double expectedGrossRevenue = (vipTickets * vipPrice * (1 - 0.15)) +
                (meiaEntradaTickets * meiaEntradaPrice) +
                (normalTickets * normalPrice * (1 - 0.15));
        double expectedTotalCost = 2000.00 * 1.15 + 1000.00;
        double expectedNetRevenue = expectedGrossRevenue - expectedTotalCost;
        String expectedReport = String.format(
                "Show Report:\n" +
                        "Artist: Davvi Duarte\n" +
                        "Date: 2024-11-30\n" +
                        "VIP Tickets Sold: %d\n" +
                        "Meia Entrada Tickets Sold: %d\n" +
                        "Normal Tickets Sold: %d\n" +
                        "Net Revenue: %.2f\n" +
                        "Financial Status: LUCRO\n",
                vipTickets, meiaEntradaTickets, normalTickets, expectedNetRevenue);
        assertEquals(expectedReport, show.generateReport());
    }

    @Test
    public void testInvalidDiscount() {
        try {
            new TicketBatch(1, 100, 0.30);
            fail("Exception expected for invalid discount");
        } catch (IllegalArgumentException e) {
            assertEquals("Desconto inválido. O desconto máximo permitido é 25%.",
                    e.getMessage());
        }
    }

    @Test
    public void testSellNonexistentTicket() {
        Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        show.addTicketBatch(batch);
        Ticket nonexistentTicket = new Ticket(999, TicketType.NORMAL, null);
        try {
            nonexistentTicket.setSold(true);
            fail("Exception expected for non-existent ticket");
        } catch (IllegalStateException e) {
            assertEquals("O ingresso não pertence a nenhum lote existente.",
                    e.getMessage());
        }
    }

    @Test
    public void testModifyStatusOfUnsoldTicket() {
        Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        show.addTicketBatch(batch);
        Ticket ticket = batch.getTickets().get(0);
        ticket.setSold(true);
        try {
            ticket.setSold(true);
            fail("Exception expected for marking an already sold ticket");
        } catch (IllegalStateException e) {
            assertEquals("O ingresso já foi vendido.", e.getMessage());
        }
    }
}
