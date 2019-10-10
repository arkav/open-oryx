package dev.arkav.openoryx.net;

import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.net.packets.PacketType;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
public class PacketMapper {
    private static Map<Byte, PacketType> map;
    private static Map<PacketType, Byte> reverseMap;

    public static void mapIds() {
        mapIds(PacketMapper.class.getResourceAsStream("/packets.xml"));
    }

    public static void mapIds(Path path) throws IOException {
        mapIds(new FileInputStream(path.toString()));
    }

    private static void mapIds(InputStream is) {
        map = new HashMap<>();
        map.clear();
        reverseMap = new HashMap<>();
        reverseMap.clear();
        try {
            SAXBuilder builder = new SAXBuilder();
            Element doc = builder.build(is).getRootElement();
            is.close();

            for (Element e : doc.getChildren()) {
                map.put(Byte.parseByte(e.getChildText("PacketId")), PacketType.parseFromString(e.getChildText("PacketName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Map.Entry<Byte, PacketType> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        Logger.log("PacketMapper", "Successfully mapped " + map.size() + " packet ids!");
    }

    public static byte get(PacketType type) {
        return reverseMap.get(type);
    }

    public static PacketType get(byte id) {
        return map.get(id);
    }
}
