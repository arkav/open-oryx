package dev.arkav.openoryx.game.models;

public class Server {
    private String host;
    private String name;
    private int port;

    public Server(String name, String host) {
        this.host = host;
        this.name = name;
        this.port = 2050;
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
