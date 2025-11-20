package org.gooseapple.game.objects.entities;

import org.gooseapple.core.math.Vector2;
import org.gooseapple.core.render.Rectangle;

public class Entity extends Rectangle {
    public Entity(Vector2 size, Vector2 position, String texturePath) {
        super(size, position, true, texturePath);
    }
}
