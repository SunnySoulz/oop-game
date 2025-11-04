package org.gooseapple.game.objects.train;

import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;

public class Locomotive extends Carriage{
    public Locomotive(Vector2 position, String texture) {
        super(position, texture);
    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        super.render(event);
    }
}
