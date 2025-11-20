package org.gooseapple.core.event.events;

public class TickEvent extends Event {

    private double deltaTime;

    public TickEvent(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }
}
