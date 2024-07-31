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
        assertEquals(0.15, batch.getDiscount());   // Verifica o desconto
        
        // Verificação dos ingressos no lote
        assertEquals(100, batch.getTickets().size()); // Verifica se 100 ingressos foram criados
    }
    
}

