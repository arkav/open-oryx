package dev.arkav.openoryx.game;

public class Pathfinder {
    /*
    private Vector2 start;
    private Vector2 end;

    private Vector2 next;

    private TileMap<Boolean> walkable;

    public Pathfinder(TileMap<Boolean> walkable) {
        this.walkable = walkable;
    }

    public Vector2 begin(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
        this.next = start.clone();
        return this.next(start);
    }

    public Vector2 next(Vector2 pos) {
        if (start == null || end == null) {
            throw new NullPointerException("You must begin a path before attempting to get the next node.");
        }

        if (pos.distanceTo(this.next) < 0.5f) {
            return this.calculateNext(pos);
        }

        if (pos.equals(end)) {
            return this.end;
        }

        return this.next;
    }

    private Vector2 calculateNext(Vector2 pos) {

    }

    private class Node extends Vector2 {
        private final float g;
        private final float h;

        public Node(float x, float y, Vector2 start, Vector2 end) {
            super(x, y);
            this.g = (float)this.distanceTo(start);
            this.h = (float)this.distanceTo(end);
        }

        public float getG() {
            return g;
        }

        public float getH() {
            return h;
        }

        public float getCost() {
            return g + h;
        }
    } */
}
