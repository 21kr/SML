package com.mrp.sml.domain.repository;

public class DiscoveredDevice {
    private final String id;
    private final String name;

    public DiscoveredDevice(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}
