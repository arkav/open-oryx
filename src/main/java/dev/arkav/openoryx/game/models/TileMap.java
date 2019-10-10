package dev.arkav.openoryx.game.models;

public class TileMap<T> {
    private final T[] tiles;
    private final int height;
    private final int width;
    public TileMap(int height, int width) {
        this.height = height;
        this.width = width;
        //noinspection unchecked
        this.tiles = (T[])new Object[height * width];
    }

    public T get(int x, int y) {
        return this.tiles[y * this.width + x];
    }

    public T get(float x, float y) {
        return this.get((int)Math.floor(x), (int)Math.floor(y));
    }

    public T get(Vector2 pos) {
        return this.get(pos.x, pos.y);
    }

    public void set(int x, int y, T value) {
        this.tiles[y * this.width + x] = value;
    }

    public void set(float x, float y, T value) {
        this.set((int)Math.floor(x), (int)Math.floor(y), value);
    }

    public void set(Vector2 pos, T value) {
        this.set(pos.x, pos.y, value);
    }

    public void fill(T item) {
        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = item;
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
