package org.gooseapple.core.render;

import javafx.scene.paint.Color;
import org.gooseapple.core.collision.PhysicsBody;
import org.gooseapple.core.event.EventHandler;
import org.gooseapple.core.event.EventManager;
import org.gooseapple.core.event.events.RenderEvent;
import org.gooseapple.core.math.Vector2;

public class Rectangle extends AbstractRenderable {
    private Texture texture;
    private double opacity = 1;
    private Vector2 textureSize;
    private Vector2 textureOffset = new Vector2(0, 0);

    private boolean hitboxDebug = false;

    private PhysicsBody physicsBody;


    public Rectangle(Vector2 size, Vector2 position) {
        super();

        this.textureSize = size;
        this.physicsBody = new PhysicsBody(this, position);
        this.physicsBody.setPosition(position);
        this.physicsBody.setCollisionSize(size);
    }

    public Rectangle(Vector2 size, Vector2 position, boolean autoRender, String texturePath) {
        this.textureSize = size;
        this.physicsBody = new PhysicsBody(this, position);
        this.physicsBody.setPosition(position);
        this.physicsBody.setCollisionSize(size);

        if (texturePath != null) {
            setTexture(new Texture(texturePath));
        }

        if (!autoRender) {
            EventManager.getInstance().deleteListener(this);
        }
    }

    public Vector2 center() {
        var temp = getPosition().clone();
        temp.add(getSize().multiply(0.5));
        return temp;
    }

    public void setCenter(Vector2 newCenter) {
        var temp = newCenter.clone();
        temp.add(getSize().multiply(-0.5));
        setPosition(temp);
    }

    public Vector2 getTextureOffset() {
        return textureOffset;
    }

    public void setTextureOffset(Vector2 textureOffset) {
        this.textureOffset = textureOffset;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2 getPosition() {
        return this.physicsBody.getPosition();
    }

    public void setHitboxDebug(boolean hitboxDebug) {
        this.hitboxDebug = hitboxDebug;
    }

    public void setPosition(Vector2 position) {
        this.physicsBody.setPosition(position);
    }

    public PhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void setPhysicsBody(PhysicsBody physicsBody) {
        this.physicsBody = physicsBody;
    }

    public Vector2 getSize() {
        return this.textureSize;
    }

    public void remove() {
        EventManager.getInstance().deleteListener(this);
        if (physicsBody != null) {
            physicsBody.onRemove();
        }
    }

    @EventHandler
    @Override
    public void render(RenderEvent event) {
        var gc = event.getGraphicsContext();

        if (opacity != 1) {
            gc.save();
            gc.setGlobalAlpha(opacity);
        }

        if (texture == null) {
            gc.setFill(Color.BLACK);
            gc.fillRect(getPosition().getX(), getPosition().getY(), getSize().getX(), getSize().getY());
        } else {
            gc.drawImage(texture.getImage(), getPosition().getX() + textureOffset.getX(), getPosition().getY() + textureOffset.getY(), getSize().getX(), getSize().getY());
        }

        if (opacity != 1) {
            gc.restore();
        }

        if (hitboxDebug) {
            gc.setFill(Color.RED);
            gc.fillRect(getPosition().getX(), getPosition().getY(), getPhysicsBody().getSize().getX(), getPhysicsBody().getSize().getY());
        }
    }

    @Override
    public boolean isInView(Vector2 screenSize) {
        double left = getPosition().getX();
        double right = getPosition().getX() + getSize().getX();
        double top = getPosition().getY();
        double bottom = getPosition().getY() + getSize().getY();

        double screenLeft = 0;
        double screenRight = screenSize.getX();
        double screenTop = 0;
        double screenBottom = screenSize.getY();

        boolean hVis = right > screenLeft && left < screenRight;
        boolean vVis = bottom > screenTop && top < screenBottom;

        return hVis && vVis;
    }
}
