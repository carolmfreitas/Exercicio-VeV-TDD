package com.example;

import java.util.ArrayList;
import java.util.List;

public class TicketBatch {
    private static final double MAX_DISCOUNT = 0.25; // 25% de desconto máximo permitido
    private int id;
    private int ticketCount;
    private double discount;
    private List<Ticket> tickets;

    public TicketBatch(int id, int ticketCount, double discount) {
        if (discount > MAX_DISCOUNT) {
            throw new IllegalArgumentException("Desconto inválido. O desconto máximo permitido é 25%.");
        }
        this.id = id;
        this.ticketCount = ticketCount;
        this.discount = discount;
        this.tickets = new ArrayList<>();

       // Calculando a quantidade de ingressos VIP e MEIA_ENTRADA
       int vipCount = (int) Math.round(ticketCount * 0.2);
       int meiaEntradaCount = (int) Math.round(ticketCount * 0.1);
       int normalCount = ticketCount - vipCount - meiaEntradaCount;
       
       // Ajustando a quantidade de VIP se necessário
       if (vipCount < ticketCount * 0.2) {
           vipCount++;
           normalCount--;
       } else if (vipCount > ticketCount * 0.3) {
           vipCount--;
           normalCount++;
       }

       // Adicionando ingressos VIP
       for (int i = 0; i < vipCount; i++) {
           tickets.add(new Ticket(i + 1, TicketType.VIP));
       }
       // Adicionando ingressos MEIA_ENTRADA
       for (int i = vipCount; i < vipCount + meiaEntradaCount; i++) {
           tickets.add(new Ticket(i + 1, TicketType.MEIA_ENTRADA));
       }
       // Adicionando ingressos NORMAIS
       for (int i = vipCount + meiaEntradaCount; i < ticketCount; i++) {
           tickets.add(new Ticket(i + 1, TicketType.NORMAL));
       }
    }

    public int getId() { 
        return id; 
    }

    public int getTicketCount() { 
        return ticketCount; 
    }

    public double getDiscount() { 
        return discount; 
    }
    
    public List<Ticket> getTickets() { 
        return tickets; 
    }
}

   


