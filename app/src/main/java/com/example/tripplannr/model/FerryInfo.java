package com.example.tripplannr.model;

public class FerryInfo {

    private String name;
    private boolean food;
    private boolean shop;

    public FerryInfo(String name, boolean food, boolean shop) {
        this.name = name;
        this.food = food;
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isShop() {
        return shop;
    }
}
