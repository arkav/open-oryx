package dev.arkav.openoryx.game.models;

import dev.arkav.openoryx.net.data.WorldPosData;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public WorldPosData toWorldPos() {
        return new WorldPosData(this.x, this.y);
    }

    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    /**
     * Clones into an existing vector
     * @param v The vector to clone into
     */
    public void clone(Vector2 v) {
        v.x = this.x;
        v.y = this.y;
    }

    public String toString() {
        return "x: " + this.x + " y: " + this.y;
    }

    public double distanceTo(float x, float y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public double distanceTo(Vector2 target) {
        return this.distanceTo(target.x, target.y);
    }

    public Vector2 subtract(Vector2 v2) {
        this.x -= v2.x;
        this.y -= v2.y;
        return this;
    }

    public Vector2 divide(float divisor) {
        this.x /= divisor;
        this.y /= divisor;
        return this;
    }

    public Vector2 normalize() {
        float norm = x > y ? x : y;
        x /= norm;
        y /= norm;
        return this;
    }
}
