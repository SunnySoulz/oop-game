package org.gooseapple.core.render;

import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;

public interface IRenderable {
    public void render(RenderEvent event);
    public boolean isInView(Vector2 screenSize);
}
