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
}

