package com.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.liferay.portal.kernel.model.Ticket;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
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

