package org.gooseapple.core.render;

import org.gooseapple.core.event.EventListener;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;

public class AbstractRenderable implements IRenderable, EventListener {
    public AbstractRenderable() {
        EventManager.getInstance().addListener(this);
    }

    @Override
    public void render(RenderEvent event) {

    }

    @Override
    public boolean isInView(Vector2 screenSize) {
        return true;
    }
}
