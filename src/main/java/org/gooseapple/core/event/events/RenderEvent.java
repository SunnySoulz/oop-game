package org.gooseapple.core.event.events;

import javafx.scene.canvas.GraphicsContext;
import org.gooseapple.core.math.Vector2;

public class RenderEvent extends Event{
    private GraphicsContext gc;
    private double fps;
    private Vector2 screenSize;

    public RenderEvent(GraphicsContext gc, double fps, Vector2 screenSize) {
        this.gc = gc;
        this.fps = fps;
        this.screenSize = screenSize;
    }

    public double getFps() {
        return fps;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public Vector2 getScreenSize() {
        return this.screenSize;
    }
}
