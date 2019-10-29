package dev.arkav.openoryx.impl;

import dev.arkav.openoryx.game.StatusParser;
import dev.arkav.openoryx.game.appspot.Endpoints;
import dev.arkav.openoryx.game.models.*;
import dev.arkav.openoryx.net.PacketIO;
import dev.arkav.openoryx.net.crypto.RSA;
import dev.arkav.openoryx.net.data.MoveRecord;
import dev.arkav.openoryx.net.data.ObjectData;
import dev.arkav.openoryx.net.data.ObjectStatusData;
import dev.arkav.openoryx.net.data.WorldPosData;
import dev.arkav.openoryx.net.listeners.ListenerStore;
import dev.arkav.openoryx.net.listeners.ListenerType;
import dev.arkav.openoryx.net.packets.PacketType;
import dev.arkav.openoryx.net.packets.c2s.*;
import dev.arkav.openoryx.net.packets.s2c.*;
import dev.arkav.openoryx.util.Http;
import dev.arkav.openoryx.util.XML;
import dev.arkav.openoryx.util.logging.Logger;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.Proxy;

@SuppressWarnings("WeakerAccess")
public class Client {
    // Basic information from the constructor
    protected Account account;

    // client information
    protected int objectId;
    protected MapInfo mapInfo;
    protected WorldPosData pos;
    protected ObjectStatus data;

    protected Server currentServer;
    protected GameState gameState;
    protected Proxy proxy;
    private long time = System.currentTimeMillis();
    protected int getTime() {
        return (int)(System.currentTimeMillis() - this.time);
    }

    // various garbage
    protected boolean sentLoad;

    // Packet io
    protected PacketIO io;
    protected ListenerStore ls;

    protected boolean connected;
    protected boolean destroyed;

    public Client(Account account) {
        this.account = account;
        this.ls = new ListenerStore();
        this.destroyed = false;
        this.connected = false;
        this.loadDefaultHooks();
    }

    private void loadDefaultHooks() {
        this.ls.hook(PacketType.AOE, (AoePacket aoe) -> {
            AoeAckPacket ack = new AoeAckPacket();
            ack.pos = this.pos;
            ack.time = this.getTime();
            this.io.send(ack);
        });

        this.ls.hook(PacketType.CREATESUCCESS, p -> {
            CreateSuccessPacket createSuccess = (CreateSuccessPacket)p;
            this.objectId = createSuccess.objectId;
            this.gameState.characterId = createSuccess.charId;
        });

        this.ls.hook(PacketType.UPDATE, (UpdatePacket update) -> {
            this.io.send(new UpdateAckPacket());
            for (ObjectData obj : update.newObjects) {
                if (obj.status.objectId == this.objectId) {
                    this.data = StatusParser.parseObject(obj.status.stats);
                    this.pos = obj.status.pos.clone();
                    Logger.log(this.account.getGuid(), "Logged in as: " + this.data.name);
                }
            }
        });

        this.ls.hook(PacketType.MAPINFO, (MapInfoPacket mapInfo) -> {
            this.mapInfo = new MapInfo(mapInfo.name, mapInfo.height, mapInfo.width);
            Logger.log(account.getGuid(), "Connected to " + mapInfo.name);
            if(!this.sentLoad) {
                if (this.gameState.characterId > 0) {
                    this.sentLoad = true;
                    LoadPacket load = new LoadPacket();
                    load.charId = this.gameState.characterId;
                    load.isFromArena = false;
                    load.isChallenger = false;
                    this.io.send(load);
                } else {
                    CreatePacket createPacket = new CreatePacket();
                    createPacket.classType = 782; // Wizard
                    createPacket.skinType = 0;
                    createPacket.isChallenger = false;
                    Logger.log(this.account.getGuid(), "Creating new character!");
                    this.io.send(createPacket);
                }
            }
        });

        this.ls.hook(PacketType.FAILURE, (FailurePacket failure) -> {
            Logger.log(this.account.getGuid(), "Received failure: " + failure.errorDescription);
            this.io.disconnect(true);
        });

        this.ls.hook(PacketType.GOTO, (GotoPacket to) -> {
            GotoAckPacket ack = new GotoAckPacket();
            ack.time = this.getTime();
            this.io.send(ack);

            if(to.objectId == this.objectId) {
                this.pos = to.pos.clone();
            }
        });

        this.ls.hook(PacketType.PING, (PingPacket ping) -> {
            PongPacket ack = new PongPacket();
            ack.serial = ping.serial;
            ack.time = this.getTime();
            this.io.send(ack);
        });

        this.ls.hook(PacketType.NEWTICK, (NewTickPacket newTick) -> {
            MovePacket ack = new MovePacket();
            ack.tickId = newTick.tickId;
            ack.time = this.getTime();
            ack.worldPos = this.pos.clone();
            ack.moveRecords = new MoveRecord[0];
            this.io.send(ack);

            for (ObjectStatusData status : newTick.statuses) {
                if (status.objectId == this.objectId) {
                    StatusParser.parseObject(status.stats, this.data);
                }
            }
        });

        this.ls.hook(ListenerType.DISCONNECT, () -> {
            this.connected = false;
            Logger.log(this.account.getGuid(), "Disconnected!");
        });

        this.ls.hook(ListenerType.CONNECT, () -> this.connected = true);
    }

    public void connect(Server server, GameState gs) {
        if (this.destroyed) return;
        if (this.io != null) this.io.disconnect(false);

        int oldCharId = this.gameState != null ? this.gameState.characterId : 0;
        this.gameState = gs;
        if (oldCharId > 0) this.gameState.characterId = oldCharId;
        this.currentServer = server;

        try {
            if (this.gameState.characterId < 1 && this.gameState.characterId != -1) { // Get characterId from appspot
                String raw = this.loadCharList();
                NodeList el = XML.parseText(raw).getDocumentElement().getElementsByTagName("Char");
                this.gameState.characterId = el.getLength() > 0 ? Integer.parseInt(el.item(0).getAttributes().getNamedItem("id").getTextContent()) : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.time = System.currentTimeMillis();

        Logger.log(this.account.getGuid(), "Connecting to: " + server.getName());

        HelloPacket hello = HelloPacket.createDefault();
        hello.buildVersion = gs.buildVersion;
        hello.gameId = gs.gameId;
        hello.guid = RSA.encrypt(this.account.getGuid());
        hello.password = RSA.encrypt(this.account.getPassword());
        hello.key = gs.key;
        hello.keyTime = gs.keyTime;
        hello.gameNet = "rotmg";
        hello.playPlatform = "rotmg";
        this.sentLoad = false;
        try {
            this.io = this.proxy == null ? new PacketIO(server, hello, this.ls) : new PacketIO(server, this.proxy, hello, this.ls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadCharList() throws IOException {
        String endpoint = Endpoints.CHAR_LIST.builder()
                .append("guid", this.account.getGuid())
                .append("password", this.account.getPassword())
                .append("challenger", "false")
                .build();
        return this.proxy == null ? Http.get(endpoint) : Http.proxiedGet(endpoint, 5000, this.proxy);
    }

    public void disconnect() {
        this.io.disconnect(false);
        this.connected = false;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void removeProxy() {
        this.proxy = null;
    }

    public void destroy() {
        this.disconnect();
        this.destroyed = true;
    }
}
