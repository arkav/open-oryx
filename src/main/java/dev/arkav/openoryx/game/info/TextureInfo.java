package dev.arkav.openoryx.game.info;

import dev.arkav.openoryx.util.OptionalXml;
import org.jdom2.Element;

public class TextureInfo {
    public static final TextureInfo DEFAULT = new TextureInfo();
    private final String file;
    private final int index;

    public TextureInfo(Element e) {
        this.file = OptionalXml.child(e, "File", "unknown");
        this.index = OptionalXml.childIntDecode(e,"Index", 0);
    }

    private TextureInfo() {
        this.file = "unknown";
        this.index = 0;
    }

    public String getFile() {
        return file;
    }

    public int getIndex() {
        return index;
    }
}
