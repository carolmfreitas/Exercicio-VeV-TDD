package com.example;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String date;
    private String artist;
    private double cache;
    private double infrastructureCost;
    private boolean specialDate;
    private List<TicketBatch> ticketBatches;

    public Show(String date, String artist, double cache, double infrastructureCost, boolean specialDate) {
        this.date = date;
        this.artist = artist;
        this.cache = cache;
        this.infrastructureCost = infrastructureCost;
        this.specialDate = specialDate;
        this.ticketBatches = new ArrayList<>();
    }

    public void addTicketBatch(TicketBatch batch) {
        this.ticketBatches.add(batch);
    }

    public List<TicketBatch> getTicketBatches() {
        return ticketBatches;
    }

    public double calculateNetRevenue() {
        double grossRevenue = 0;
        for (TicketBatch batch : ticketBatches) {
            for (Ticket ticket : batch.getTickets()) {
                if (ticket.isSold()) {
                    double price = getTicketPrice(ticket);
                    grossRevenue += price * (1 - batch.getDiscount()); // Aplica o desconto
                }
            }
        }

        double totalCost = infrastructureCost;
        if (specialDate) {
            totalCost += infrastructureCost * 0.15; // Ajuste correto para data especial
        }
        totalCost += cache;

        return grossRevenue - totalCost;
    }

    private double getTicketPrice(Ticket ticket) {
        switch (ticket.getType()) {
            case VIP:
                return 20.00;  // Preço do ingresso VIP
            case MEIA_ENTRADA:
                return 5.00;   // Preço do ingresso MEIA_ENTRADA
            case NORMAL:
            default:
                return 10.00;  // Preço do ingresso NORMAL
        }
    }

}
