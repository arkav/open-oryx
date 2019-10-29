package dev.arkav.openoryx.game.appspot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EndpointBuilder {

    private String fullURL;

    private boolean hasAppendedQ = false;

    public EndpointBuilder(String url) {
        this.fullURL = url;
    }

    public EndpointBuilder append(String parameter, String data) {
        if(!this.hasAppendedQ) {
            this.hasAppendedQ = true;
            this.fullURL += "?" + parameter + "=" + encodeValue(data);
        } else {
            this.fullURL += "&" + parameter + "=" + encodeValue(data);
        }
        return this;
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public String build() {
        return this.fullURL;
    }
}
