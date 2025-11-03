package org.gooseapple.core.math;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public void add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 multiply(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        double length = length();

        if (length == 0) {
            return new Vector2(0,0);
        } else {
            return new Vector2(x/length, y/length);
        }
    }

    @Override
    public String toString() {
        return "X:" + this.x + " | Y:" + this.y;
    }
}
