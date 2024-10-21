package com.example.partitioning_demo.partitioning.entities;

public enum Partition {
    ASIA("Asia"),
    AFRICA("Africa"),
    NORTH_AMERICA("North America"),
    SOUTH_AMERICA("South America"),
    EUROPE("Europe"),
    AUSTRALIA("Australia"),
    ;
 
    private final String key;
 
    Partition(String key) {
        this.key = key;
    }
 
    public String getKey() {
        return key;
    }
}
