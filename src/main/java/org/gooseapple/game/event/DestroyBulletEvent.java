package org.gooseapple.game.event;

import org.gooseapple.core.event.events.Event;
import org.gooseapple.game.objects.Bullet;

public class DestroyBulletEvent extends Event {
    private Bullet bullet;
    public DestroyBulletEvent(Bullet bullet) {
        this.bullet = bullet;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
