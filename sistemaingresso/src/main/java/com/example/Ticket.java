package com.example;

public class Ticket {
    
    private int id;
    private TicketType type;
    private boolean isSold;

    public Ticket(int id, TicketType type) {
       this.id = id;
       this.type = type;
       this.isSold = false;
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
        this.isSold = isSold; 
    }
}
