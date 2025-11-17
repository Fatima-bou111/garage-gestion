package com.renault.garage.events;


import java.time.Instant;

public class VehicleEvent {
    private Long id;
    private String name;
    private String action;
    private Instant timestamp;

    public VehicleEvent() {}

    public VehicleEvent(Long id, String name, String action, Instant timestamp) {
        this.id = id;
        this.name = name;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}

