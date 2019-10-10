package dev.arkav.openoryx.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XML {
    public static Document parseText(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static boolean containsChild(String name, NodeList children) {
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals(name)) return true;
        }
        return false;
    }
}
