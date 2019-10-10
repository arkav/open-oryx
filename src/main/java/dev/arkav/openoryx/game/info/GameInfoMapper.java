package dev.arkav.openoryx.game.info;

import dev.arkav.openoryx.util.logging.Logger;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GameInfoMapper {
    public static ArrayList<ObjectInfo> objects = new ArrayList<>();
    public static ArrayList<TileInfo> tiles = new ArrayList<>();

    public static void mapObjects() {
        mapObjects(GameInfoMapper.class.getResourceAsStream("/objects.xml"));
    }

    public static void mapObjects(Path path) throws IOException {
        mapObjects(new FileInputStream(path.toString()));
    }

    private static void mapObjects(InputStream is) {
        objects.clear();
        try {
            SAXBuilder builder = new SAXBuilder();
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            InputSource in = new InputSource(reader);
            Element doc = builder.build(in).getRootElement();
            reader.close();
            is.close();


            int errored = 0;
            for (Element e : doc.getChildren()) {
                try {
                    objects.add(new ObjectInfo(e));
                } catch (Exception ex) {
                    errored++;
                }
            }
            Logger.log("GameInfoMapper", "Loaded " + objects.size() + " game entities! Errored: " + errored);
        } catch (Exception e) {
            Logger.log("GameInfoMapper", "Error while reading entities.xml");
            e.printStackTrace();
        }
    }

    public static void mapGroundTypes() {
        mapGroundTypes(GameInfoMapper.class.getResourceAsStream("/tiles.xml"));
    }

    public static void mapGroundTypes(Path path) throws IOException {
        mapGroundTypes(new FileInputStream(path.toString()));
    }

    private static void mapGroundTypes(InputStream is) {
        tiles.clear();
        try {
            SAXBuilder builder = new SAXBuilder();
            Element doc = builder.build(is).getRootElement();
            is.close();
            for (Element e : doc.getChildren()) {
                tiles.add(new TileInfo(e));
            }
            Logger.log("GameInfoMapper", "Loaded " + tiles.size() + " tiles!");
        } catch (Exception e) {
            Logger.log("GameInfoMapper", "Error while reading tiles.xml");
        }
    }



    public static ObjectInfo objectById(int id) {
        for (ObjectInfo obj : objects) {
            if (obj.getId() == id) return obj;
        }
        return ObjectInfo.DEFAULT;
    }

    public static ObjectInfo objectByName(String name) {
        for (ObjectInfo obj : objects) {
            if (obj.getName().equals(name)) return obj;
        }
        return ObjectInfo.DEFAULT;
    }

    public static TileInfo tileById(int id) {
        for (TileInfo tile : tiles) {
            if (tile.getId() == id) return tile;
        }
        return TileInfo.DEFAULT;
    }

    public static TileInfo tileByName(String name) {
        for (TileInfo tile : tiles) {
            if (tile.getName().equals(name)) return tile;
        }
        return TileInfo.DEFAULT;
    }
}
