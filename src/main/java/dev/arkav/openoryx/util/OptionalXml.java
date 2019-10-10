package dev.arkav.openoryx.util;

import org.jdom2.Element;

/**
 * Utility class for getting optional xml elements
 */
@SuppressWarnings("ALL")
public class OptionalXml {
    public static boolean child(Element e, String name) {
        return e.getChild(name) != null;
    }

    public static String child(Element e, String name, String opt) {
        return child(e, name) ? e.getChildText(name) : opt;
    }

    public static int childInt(Element e, String name, int opt) {
        return child(e, name) ? Integer.parseInt(e.getChildText(name)) : opt;
    }

    public static int childIntDecode(Element e, String name, int opt) {
        return child(e, name) ? Integer.decode(e.getChildText(name)) : opt;
    }

    public static int childIntHex(Element e, String name, int opt) {
        return child(e, name) ? Integer.parseInt(e.getChildText(name), 16) : opt;
    }

    public static float childFloat(Element e, String name, float opt) {
        return child(e, name) ? Float.parseFloat(e.getChildText(name)) : opt;
    }

    public static boolean attribute(Element e, String name) {
        return e.getAttribute(name) != null;
    }

    public static String attribute(Element e, String name, String opt) {
        return attribute(e, name) ? e.getAttributeValue(name) : opt;
    }

    public static int attributeIntDecode(Element e, String name, int opt) {
        return attribute(e, name) ? Integer.decode(e.getAttributeValue(name)) : opt;
    }
}
