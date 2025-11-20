package org.gooseapple.core.collision;

import com.github.davidmoten.rtree2.Entry;
import com.github.davidmoten.rtree2.RTree;
import com.github.davidmoten.rtree2.geometry.Rectangle;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventListener;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.events.CollisionEvent;
import org.gooseapple.core.event.events.TickEvent;
import org.gooseapple.core.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class PhysicsService implements EventListener {
    private static PhysicsService physicsService = new PhysicsService();
    private RTree<UUID, Rectangle> physicsEntities = RTree.create();
    private ArrayList<PhysicsBody> activePhysicsBodies = new ArrayList<>(); //idk if i'll use this but for everything with a velocity > 0
    private HashMap<UUID, PhysicsBody> activePhysicsBodiesMap = new HashMap<>();

    private double physicsSpeed = 70;

    public PhysicsService() {
        EventManager.getInstance().addListener(this);
    }

    public static PhysicsService get() {
        return physicsService;
    }

    public void add(PhysicsBody physicsBody) {
        activePhysicsBodies.add(physicsBody);
        activePhysicsBodiesMap.put(physicsBody.getID(), physicsBody);
        physicsEntities = physicsEntities.add(physicsBody.getID(), physicsBody.toGeometry());
    }

    public void remove(PhysicsBody physicsBody) {
        activePhysicsBodies.remove(physicsBody);
        activePhysicsBodiesMap.remove(physicsBody.getID());
        physicsEntities = physicsEntities.delete(physicsBody.getID(), physicsBody.toGeometry());
    }

    private boolean isActive(PhysicsBody physicsBody) {
        return physicsBody.getVelocity().length() > 0;
    }

    public void markActive(PhysicsBody physicsBody) {
        if (!activePhysicsBodies.contains(physicsBody)) {
            activePhysicsBodies.add(physicsBody);
        }
    }

    @EventHandler
    public void handleTick(TickEvent event) {
        for (int i = 0; i < activePhysicsBodies.size(); i++) {
            PhysicsBody physicsBody = activePhysicsBodies.get(i);
            if (!isActive(physicsBody)) {
                activePhysicsBodies.remove(i);
                continue;
            }

            if(physicsBody.isAffectedByGravity()) {
                physicsBody.getVelocity().add(new Vector2(0, physicsSpeed * 1/16d * event.getDeltaTime()));
            }
            Rectangle oldGeometry = physicsBody.toGeometry();

            physicsBody.getPosition().add(physicsBody.getVelocity().clone().multiply(event.getDeltaTime() * physicsSpeed));

            physicsEntities = physicsEntities
                    .delete(physicsBody.getID(), oldGeometry)
                    .add(physicsBody.getID(), physicsBody.toGeometry());
        }

        runCollisionChecks(event);
    }

    private void runCollisionChecks(TickEvent event) {
        try {
            for (PhysicsBody body : new ArrayList<>(activePhysicsBodies)) {

                Rectangle geom = body.toGeometry();

                var potentialCollisions = physicsEntities.search(geom).iterator();

                for (Iterator<Entry<UUID, Rectangle>> it = potentialCollisions; it.hasNext(); ) {
                    Entry<UUID, Rectangle> collision = it.next();
                    UUID id = collision.value();
                    PhysicsBody mappedValue = activePhysicsBodiesMap.get(id);

                    if (mappedValue.isCollisionEnabled() && body.isCollisionEnabled()) {
                        if (mappedValue != null && mappedValue != body) {
                            handleCollision(mappedValue, body);
                        }
                    }
                }
            }

        } catch (Exception ex) {

        }
    }

    private void handleCollision(PhysicsBody a, PhysicsBody b) {
        Vector2 aPos = a.getPosition();
        Vector2 aSize = a.getSize();
        Vector2 bPos = b.getPosition();
        Vector2 bSize = b.getSize();

        boolean overlapX = aPos.getX() + aSize.getX() > bPos.getX() && aPos.getX() < bPos.getX() + bSize.getX();
        boolean overlapY = aPos.getY() + aSize.getY() > bPos.getY() && aPos.getY() < bPos.getY() + bSize.getY();

        if (overlapX && overlapY) {
            CollisionEvent event = new CollisionEvent(a, b);
            event.dispatch();
        }
    }
}
