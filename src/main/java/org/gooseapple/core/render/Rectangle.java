package org.gooseapple.core.render;

import javafx.scene.paint.Color;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventListener;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.event.events.TickEvent;
import org.gooseapple.core.math.Vector2;

public class Rectangle extends AbstractRenderable {
    private Vector2 size;
    private Vector2 position;
    private Vector2 velocity;

    public Rectangle(Vector2 size, Vector2 position) {
        super();
        this.size = size;
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    @EventHandler
    public void tick(TickEvent event) {
        this.position.add(this.velocity);
    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        if (!isInView(event.getScreenSize())) {
            this.velocity.reverse();
            return;
        }

        var gc = event.getGraphicsContext();

        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

    @Override
    public boolean isInView(Vector2 screenSize) {
        double left = position.getX();
        double right = position.getX() + size.getX();
        double top = position.getY();
        double bottom = position.getY() + size.getY();

        double screenLeft = 0;
        double screenRight = screenSize.getX();
        double screenTop = 0;
        double screenBottom = screenSize.getY();

        boolean hVis = right > screenLeft && left < screenRight;
        boolean vVis = bottom > screenTop && top < screenBottom;

        return hVis && vVis;
    }
}
