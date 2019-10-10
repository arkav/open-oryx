package dev.arkav.openoryx.game.appspot;

import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.game.models.Server;
import dev.arkav.openoryx.util.Http;
import dev.arkav.openoryx.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ServerMapper {
    private static Map<String, Server> servers = new HashMap<>();

    public static void mapServers(boolean useCache) {
        if (useCache) {
            try {
                FileInputStream fs = new FileInputStream("servers.dat");
                ObjectInputStream ois = new ObjectInputStream(fs);
                int size = ois.readInt();
                for (int i = 0; i < size; i++) {
                    String name = ois.readUTF();
                    servers.put(name, new Server(name, ois.readUTF()));
                }
                ois.close();
                fs.close();
            } catch (Exception e) {
                Logger.log("ServerMapper", "Error while reading servers.dat, fetching servers from appspot!");
                mapFromAppspot();
            }
            Logger.log("ServerMapper", "Successfully loaded " + servers.size() + " servers from servers.dat!");
        } else mapFromAppspot();
    }

    private static void mapFromAppspot() {
        try {
            String raw = Http.get(new EndpointFactory(Endpoints.CHAR_LIST).append("guid", String.valueOf(Math.random() * 10000000)).get());
            Document xml = XML.parseText(raw);

            xml.getDocumentElement().normalize();
            NodeList nodes = xml.getElementsByTagName("Server");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    servers.put(element.getElementsByTagName("Name").item(0).getTextContent(), new Server(element.getElementsByTagName("Name").item(0).getTextContent(), element.getElementsByTagName("DNS").item(0).getTextContent()));
                }
            }

            try {
                FileOutputStream fs = new FileOutputStream("servers.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fs);
                oos.writeInt(servers.size());
                for (Server server : servers.values()) {
                    oos.writeUTF(server.getName());
                    oos.writeUTF(server.getHost());
                }
                oos.close();
                fs.close();
            } catch (Exception e) {
                Logger.log("ServerMapper", "Unable to save servers.dat");
                e.printStackTrace();
            }

            Logger.log("ServerMapper", "Mapped " + servers.size() + " servers!");
        } catch (Exception e) {
            Logger.log("ServerMapper", "Unable to map servers at this time!");
            e.printStackTrace();
        }
    }

    public static Server get(String name) {
        return servers.get(name);
    }
}
