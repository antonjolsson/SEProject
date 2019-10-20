package com.example.tripplannr.domain_layer;

public class FerryInfo {

    private final String name;
    private final boolean food;
    private final boolean largeBorderShop;
    private final boolean conference;
    private final boolean lounge;
    private final String url;

    public FerryInfo(String name, boolean food, boolean largeBorderShop, boolean conference, boolean lounge, String url) {
        this.name = name;
        this.food = food;
        this.largeBorderShop = largeBorderShop;
        this.conference = conference;
        this.lounge = lounge;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isLargeBorderShop() {
        return largeBorderShop;
    }

    public boolean isConference() {
        return conference;
    }

    public boolean isLounge() {
        return lounge;
    }

    public String getUrl() {
        return url;
    }
}