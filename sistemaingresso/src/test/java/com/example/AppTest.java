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
}

