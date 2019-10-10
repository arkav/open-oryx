package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.Environment;
import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class HelloPacket implements Packet {
    public String buildVersion;
    public int gameId;
    public String guid;
    public String password;
    public String secret;
    public int keyTime;
    public byte[] key;
    public String mapJSON;
    public String entryTag;
    public String gameNet;
    public String gameNetUserId;
    public String playPlatform;
    public String platformToken;
    public String userToken;
    public String clientToken;

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.buildVersion);
        out.writeInt(this.gameId);
        out.writeUTF(this.guid);
        out.writeInt((int)Math.round((Math.random() * 1000000000))); //random1
        out.writeUTF(this.password);
        out.writeInt((int)Math.round((Math.random() * 1000000000))); //random2
        out.writeUTF(this.secret);
        out.writeInt(this.keyTime);
        out.writeShort(this.key.length);
        out.write(this.key);
        byte[] mapJSON = this.mapJSON.getBytes();
        out.writeInt(mapJSON.length);
        out.write(mapJSON);
        out.writeUTF(this.entryTag);
        out.writeUTF(this.gameNet);
        out.writeUTF(this.gameNetUserId);
        out.writeUTF(this.playPlatform);
        out.writeUTF(this.platformToken);
        out.writeUTF(this.userToken);
        out.writeUTF(this.clientToken);
    }

    public void read(DataInput in) throws IOException {
        this.buildVersion = in.readUTF();
        this.gameId = in.readInt();
        this.guid = in.readUTF();
        in.readInt(); //random1
        this.password = in.readUTF();
        in.readInt(); //random2
        this.secret = in.readUTF();
        this.keyTime = in.readInt();
        this.key = new byte[in.readShort()];
        in.readFully(this.key);
        byte[] mapJson = new byte[in.readInt()];
        in.readFully(mapJson);
        this.mapJSON = new String(mapJson);
        this.entryTag = in.readUTF();
        this.gameNet = in.readUTF();
        this.gameNetUserId = in.readUTF();
        this.playPlatform = in.readUTF();
        this.platformToken = in.readUTF();
        this.userToken = in.readUTF();
        this.clientToken = in.readUTF();
    }

    public static HelloPacket createDefault() {
        HelloPacket p = new HelloPacket();
        p.mapJSON = "";
        p.entryTag = "";
        p.secret = "";
        p.userToken = "";
        p.gameNetUserId = "";
        p.platformToken = "";
        p.keyTime = -1;
        p.clientToken = Environment.CLIENT_TOKEN;
        return p;
    }
}
