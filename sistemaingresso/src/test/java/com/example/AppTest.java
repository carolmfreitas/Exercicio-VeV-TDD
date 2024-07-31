package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTest 
{
   
    @Test
    public void testCreateTicket() {
        // Criação de um ingresso do tipo NORMAL com ID 1
        Ticket ticket = new Ticket(1, TicketType.NORMAL);
        
        // Verificação dos atributos
        assertEquals(1, ticket.getId());           // Verifica se o ID está correto
        assertEquals(TicketType.NORMAL, ticket.getType()); // Verifica se o tipo está correto
        assertFalse(ticket.isSold());              // Verifica se o status inicial é não vendido
    }

    @Test
    public void testMarkTicketAsSold() {
        // Criação de um ingresso do tipo NORMAL com ID 1
        Ticket ticket = new Ticket(1, TicketType.NORMAL);
        
        // Verificação do status inicial (não vendido)
        assertFalse(ticket.isSold());
        
        // Marcar o ingresso como vendido
        ticket.setSold(true);
        
        // Verificação do status após marcar como vendido
        assertTrue(ticket.isSold());
    }

    @Test
    public void testCreateTicketBatch() {
        // Criação de um lote de ingressos
        TicketBatch batch = new TicketBatch(1, 100, 0.15); // Lote com 100 ingressos e 15% de desconto
        
        // Verificação dos atributos do lote
        assertEquals(1, batch.getId());            // Verifica se o ID está correto
        assertEquals(100, batch.getTicketCount()); // Verifica a quantidade de ingressos
        assertEquals(0.15, batch.getDiscount(), 0.0001);   // Verifica o desconto
        
        // Verificação dos ingressos no lote
        assertEquals(100, batch.getTicketCount()); // Verifica se 100 ingressos foram criados
    }

    @Test
    public void testTicketTypeProportion() {
        // Criação de um lote de 100 ingressos com 15% de desconto
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
        
        // Verificação das proporções
        assertTrue(vipCount >= 20 && vipCount <= 30);
        assertEquals(10, meiaEntradaCount);
        assertEquals(100 - vipCount - meiaEntradaCount, normalCount);
    }


    @Test
    public void testAddTicketBatch() {
        //criação de um show
        Show show = new Show("2024-11-30", "Davvi Duarte", 2500.0, 5000.0, true);
        TicketBatch batch = new TicketBatch(1, 100, 0.15);
        show.addTicketBatch(batch);

        //verificação se um show tem 1 lote 
        assertEquals(1, show.getTicketBatches().size());
        //verificação de lote criado
        assertEquals(batch, show.getTicketBatches().get(0));
    }



    @Test
    public void testCalculateNetRevenue() {
        // Configura um show com cachê do artista de R$ 1.000,00, custos de infraestrutura de R$ 2.000,00 e data especial
        Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);

        // Adiciona um lote de ingressos com 500 ingressos e 15% de desconto
        TicketBatch batch = new TicketBatch(1, 500, 0.15);
        for (Ticket ticket : batch.getTickets()) {
            ticket.setSold(true); // Marca todos os ingressos como vendidos
        }
        show.addTicketBatch(batch);

        // Calcula a receita bruta esperada
        // Considera que 20% dos ingressos são VIP, 10% são MEIA_ENTRADA e o restante são NORMAL
        int totalTickets = 500;
        int vipTickets = (int) (totalTickets * 0.20);
        int meiaEntradaTickets = (int) (totalTickets * 0.10);
        int normalTickets = totalTickets - vipTickets - meiaEntradaTickets;

        double vipPrice = 20.00;
        double meiaEntradaPrice = 5.00;
        double normalPrice = 10.00;

        double expectedGrossRevenue = 
            (vipTickets * vipPrice * (1 - 0.15)) +
            (meiaEntradaTickets * meiaEntradaPrice) +
            (normalTickets * normalPrice * (1 - 0.15));

        // Calcula o custo total esperado com ajuste para data especial (15% adicional)
        double expectedTotalCost = 2000.00 * 1.15 + 1000.00;

        // Calcula a receita líquida esperada
        double expectedNetRevenue = expectedGrossRevenue - expectedTotalCost;

        // Verifica se a receita líquida calculada é igual à esperada
        assertEquals(expectedNetRevenue, show.calculateNetRevenue(), 0.01);
    }


    @Test
    public void testReportGeneration() {
    // Configura um show com cachê do artista de R$ 1.000,00, custos de infraestrutura de R$ 2.000,00 e data especial
    Show show = new Show("2024-11-30", "Davvi Duarte", 1000.00, 2000.00, true);

    // Adiciona um lote de ingressos com 500 ingressos e 15% de desconto
    TicketBatch batch = new TicketBatch(1, 500, 0.15);
    for (Ticket ticket : batch.getTickets()) {
        ticket.setSold(true); // Marca todos os ingressos como vendidos
    }
    show.addTicketBatch(batch);

    // Calcula a receita líquida esperada
    int totalTickets = 500;
    int vipTickets = (int) (totalTickets * 0.20);
    int meiaEntradaTickets = (int) (totalTickets * 0.10);
    int normalTickets = totalTickets - vipTickets - meiaEntradaTickets;

    double vipPrice = 20.00;
    double meiaEntradaPrice = 5.00;
    double normalPrice = 10.00;

    double expectedGrossRevenue = 
        (vipTickets * vipPrice + meiaEntradaTickets * meiaEntradaPrice + normalTickets * normalPrice) * (1 - 0.15);

    double expectedTotalCost = 2000.00 * 1.15 + 1000.00;
    double expectedNetRevenue = expectedGrossRevenue - expectedTotalCost;

    // Define a saída esperada do relatório
    String expectedReport = String.format(
        "Show Report:\n" +
        "Artist: Davvi Duarte\n" +
        "Date: 2024-11-30\n" +
        "VIP Tickets Sold: %d\n" +
        "Meia Entrada Tickets Sold: %d\n" +
        "Normal Tickets Sold: %d\n" +
        "Net Revenue: %.2f\n" +
        "Financial Status: LUCRO\n",
        vipTickets, meiaEntradaTickets, normalTickets, expectedNetRevenue
    );

    // Verifica se o relatório gerado corresponde ao esperado
    assertEquals(expectedReport, show.generateReport());
}
}

