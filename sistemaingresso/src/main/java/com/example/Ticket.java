package com.example;

public class Ticket {
    
    private int id;
    private TicketType type;
    private boolean isSold;
    private TicketBatch batch;

    public Ticket(int id, TicketType type, TicketBatch batch) {
       this.id = id;
       this.type = type;
       this.isSold = false;
       this.batch = batch;
    }

    public int getId() { 
        return id; 
    }

    public TicketType getType() { 
        return type; 
    }

    public boolean isSold() { 
        return isSold;
    }

    public void setSold(boolean isSold) {
        if (batch == null) {
            throw new IllegalStateException("O ingresso não pertence a nenhum lote existente.");
        } 
        this.isSold = isSold; 
    }
}
