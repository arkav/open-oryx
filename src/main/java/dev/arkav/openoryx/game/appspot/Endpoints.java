package dev.arkav.openoryx.game.appspot;

public enum Endpoints {
    CHAR_LIST("https://realmofthemadgod.appspot.com/char/list")
    ;
    private final String url;
    Endpoints(String url) {
        this.url = url;
    }

    public EndpointBuilder builder() {
        return new EndpointBuilder(this.url);
    }
}
