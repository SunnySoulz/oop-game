package org.gooseapple.game.event;

import org.gooseapple.core.event.events.Event;
import org.gooseapple.game.objects.Bullet;
import org.gooseapple.game.objects.entities.Entity;

public class BulletHitEvent extends Event {
    private Bullet bullet;
    private Entity entity;

    public BulletHitEvent(Bullet bullet,  Entity entity) {
        this.bullet = bullet;
        this.entity = entity;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Entity getEntity() {
        return entity;
    }
}
