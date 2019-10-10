package dev.arkav.openoryx.game.appspot;

public class EndpointFactory {

    private String fullURL;

    private boolean hasAppendedQ = false;

    public EndpointFactory(String url) {
        this.fullURL = url;
    }

    public EndpointFactory append(String parameter, String data) {
        data = data.replace("+", "%2B");
        if(!this.hasAppendedQ) {
            this.hasAppendedQ = true;
            this.fullURL += "?" + parameter + "=" + data;
        } else {
            this.fullURL += "&" + parameter + "=" + data;
        }
        return this;
    }

    public String get() {
        return this.fullURL;
    }
}
