package org.gooseapple.game.objects;

import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.event.events.TickEvent;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;
import org.gooseapple.game.event.DestroyBulletEvent;

import java.util.Random;

public class Bullet extends Rectangle {
    private static final double gravityForce = 0.05;
    private int ranTicksBeforeExplosion;

    public Bullet(Vector2 position) {
        super(new Vector2(2,2), position);
        Random random = new Random();
        ranTicksBeforeExplosion = random.nextInt(10,60);
    }

    @EventHandler
    @Override
    public void tick(TickEvent event) {

        if (getVelocity() != null) {
            Vector2 v = getVelocity();
            v.setY(v.getY() + gravityForce);

            if (v.getY() > 0) {
                ranTicksBeforeExplosion -= 1;
                if (ranTicksBeforeExplosion == 0) {
                    DestroyBulletEvent bulletEvent = new DestroyBulletEvent(this);
                    bulletEvent.dispatch();
                    EventManager.getInstance().deleteListener(this);
                }
            }
        }

        super.tick(event);
    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        super.render(event);
    }
}
