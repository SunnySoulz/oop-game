package org.gooseapple.core.collision;

import com.github.davidmoten.rtree2.geometry.Geometries;
import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;

import java.util.UUID;

public class PhysicsBody {
    private Rectangle parent;
    private Vector2 velocity;
    private Vector2 position;
    private Vector2 size;
    private boolean collisionEnabled = false;
    private UUID bodyID;

    private boolean isAffectedByGravity = true;

    public PhysicsBody(Rectangle parent, Vector2 position) {
        this.parent = parent;
        this.bodyID = UUID.randomUUID();
        this.size = parent.getSize().clone();
        this.position = position;
        this.velocity = new Vector2(0,0);

        PhysicsService.get().add(this);
    }

    public void onRemove() {
        PhysicsService.get().remove(this);
    }

    public Rectangle getParent() {
        return parent;
    }

    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }

    public void setCollisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }

    public boolean isAffectedByGravity() {
        return isAffectedByGravity;
    }

    public void setAffectedByGravity(boolean affectedByGravity) {
        isAffectedByGravity = affectedByGravity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public com.github.davidmoten.rtree2.geometry.Rectangle toGeometry() {
        return Geometries.rectangle(
                this.position.getX(),
                this.position.getY(),
                this.position.getX() + this.size.getX(),
                this.position.getY() + this.size.getY()
        );
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setCollisionSize(Vector2 size) {
        this.size = size;
    }

    public UUID getID() {
        return bodyID;
    }

    public Vector2 getVelocity() {
        onVelocityUpdated();
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        onVelocityUpdated();
        this.velocity = velocity;
    }

    private void onVelocityUpdated() {
        PhysicsService.get().markActive(this);
    }
}
