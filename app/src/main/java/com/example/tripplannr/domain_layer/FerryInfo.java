package com.example.tripplannr.domain_layer;

public class FerryInfo {

    private String name;
    private boolean food;
    private boolean largeBorderShop;
    private boolean conference;
    private boolean lounge;
    private String url;

    public FerryInfo(String name, boolean food, boolean largeBorderShop,boolean conference, boolean lounge, String url) {
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
