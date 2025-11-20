package org.gooseapple.core.event.events;

import org.gooseapple.core.collision.PhysicsBody;

public class CollisionEvent extends Event {
    private PhysicsBody firstBody;
    private PhysicsBody secondBody;

    public CollisionEvent(PhysicsBody physicsBody, PhysicsBody physicsBody2) {
        this.firstBody = physicsBody;
        this.secondBody = physicsBody2;
    }

    public PhysicsBody getFirstBody() {
        return firstBody;
    }

    public PhysicsBody getSecondBody() {
        return secondBody;
    }
}
