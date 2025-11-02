package org.gooseapple.core.math;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void add(Vector2 velocity) {
        this.x += velocity.x;
        this.y += velocity.y;
    }

    public void reverse() {
        this.x = -this.x;
        this.y = -this.y;
    }

    @Override
    public String toString() {
        return "X:" + this.x + " | Y:" + this.y;
    }
}
