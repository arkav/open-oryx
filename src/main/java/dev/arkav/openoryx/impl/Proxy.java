package dev.arkav.openoryx.impl;

import dev.arkav.openoryx.Environment;
import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.game.models.Server;
import dev.arkav.openoryx.net.ProxyIO;
import dev.arkav.openoryx.net.listeners.ListenerStore;
import dev.arkav.openoryx.net.listeners.ListenerType;
import dev.arkav.openoryx.net.packets.PacketType;
import dev.arkav.openoryx.net.packets.s2c.MapInfoPacket;
import dev.arkav.openoryx.net.packets.s2c.TextPacket;

import java.io.IOException;
import java.net.Socket;

@SuppressWarnings("WeakerAccess")
public class Proxy implements Runnable {
    @SuppressWarnings("WeakerAccess")
    protected ProxyIO client;
    @SuppressWarnings("WeakerAccess")
    protected ProxyIO server;

    @SuppressWarnings("WeakerAccess")
    protected ListenerStore ls;

    @SuppressWarnings("WeakerAccess")
    protected boolean connected;

    public Proxy(Socket clientSocket, Server server) throws IOException {
        this.ls = new ListenerStore();
        this.server = new ProxyIO(new Socket(server.getHost(), server.getPort()), this.ls, Environment.RC4_SERVER_KEY, Environment.RC4_CLIENT_KEY);
        this.client = new ProxyIO(clientSocket, this.ls, Environment.RC4_CLIENT_KEY, Environment.RC4_SERVER_KEY);
        this.server.setPartner(this.client);
        this.client.setPartner(this.server);

        this.ls.hook(ListenerType.DISCONNECT, () -> {
            this.client.disconnect();
            this.server.disconnect();
            this.connected = false;
        });
        this.ls.hook(PacketType.MAPINFO, (MapInfoPacket p) -> {
            Logger.log("Proxy", String.format("Connected to %s %s", server.getName(), p.name));
            TextPacket tp = TextPacket.createDefault();
            tp.text = "Welcome to Open Oryx proxy!";
            tp.cleanText = "Welcome to Open Oryx proxy!";
            this.client.send(tp);
        });
    }

    public ProxyIO getClient() {
        return client;
    }

    public ProxyIO getServer() {
        return server;
    }

    public ListenerStore getListenerStore() {
        return ls;
    }

    @Override
    public void run() {
        this.client.start();
        this.server.start();
        this.connected = true;
    }
}
