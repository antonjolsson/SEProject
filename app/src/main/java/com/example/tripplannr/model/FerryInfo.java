package com.example.tripplannr.model;

public class FerryInfo {

    private String name;
    private boolean food;
    private boolean largShop;
    private boolean lounge;
    private boolean conference;

    public FerryInfo(String name, boolean food, boolean shop,boolean lounge, boolean conference) {
        this.name = name;
        this.food = food;
        this.largShop = shop;
        this.lounge = lounge;
        this.conference = conference;
    }

    public String getName() {
        return name;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isShop() {
        return largShop;
    }

    public boolean isLounge() {return lounge;    }

    public boolean isConference() {return conference;    }
}
