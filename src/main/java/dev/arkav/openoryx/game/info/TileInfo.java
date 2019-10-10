package dev.arkav.openoryx.game.info;

import dev.arkav.openoryx.util.OptionalXml;
import org.jdom2.Element;

public class TileInfo {
    public static final TileInfo DEFAULT = new TileInfo();
    private final int id;
    private final String name;
    private final boolean noWalk;
    private final float speed;
    private final TextureInfo texture;

    public TileInfo(Element e) {
        this.id = Integer.decode(e.getAttributeValue("type"));
        this.name = e.getAttributeValue("id");
        this.noWalk = OptionalXml.child(e, "NoWalk");
        this.speed = OptionalXml.childFloat(e, "Speed", 0);
        this.texture = OptionalXml.child(e, "Texture") ? new TextureInfo(e.getChild("Texture")) : TextureInfo.DEFAULT;
    }

    public TileInfo() {
        this.id = 0;
        this.name = "unknown";
        this.noWalk = true;
        this.speed = 0;
        this.texture = TextureInfo.DEFAULT;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isNoWalk() {
        return noWalk;
    }

    public float getSpeed() {
        return speed;
    }

    public TextureInfo getTexture() {
        return texture;
    }
}
