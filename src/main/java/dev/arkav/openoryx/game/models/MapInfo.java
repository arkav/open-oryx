package dev.arkav.openoryx.game.models;

public class MapInfo {
    private String name;
    private int height;
    private int width;

    public MapInfo (String name, int height, int width) {
        this.name = name;
        this.height = height;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
