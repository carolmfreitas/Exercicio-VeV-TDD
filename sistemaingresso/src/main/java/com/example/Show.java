package com.example;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String date;
    private String artist;
    private double cache;
    private double infrastructureExpenses;
    private boolean specialDate;
    private List<TicketBatch> ticketBatches;

    public Show(String date, String artist, double cache, double infrastructureExpenses, boolean specialDate) {
        this.date = date;
        this.artist = artist;
        this.cache = cache;
        this.infrastructureExpenses = infrastructureExpenses;
        this.specialDate = specialDate;
        this.ticketBatches = new ArrayList<>();
    }

    public void addTicketBatch(TicketBatch batch) {
        this.ticketBatches.add(batch);
    }

    public List<TicketBatch> getTicketBatches() {
        return ticketBatches;
    }

}
