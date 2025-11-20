package org.gooseapple.game.objects.entities;

import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;

public class Zeppelin extends Entity {
    public Zeppelin(Vector2 position) {
        super(new Vector2(150,90), position, "textures/entities/zeppelin.png");
        getPhysicsBody().setCollisionSize(new Vector2(70,40));
        getPhysicsBody().setAffectedByGravity(false);
        getPhysicsBody().setCollisionEnabled(true);

        setHitboxDebug(true);

    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        super.render(event);
    }
}
