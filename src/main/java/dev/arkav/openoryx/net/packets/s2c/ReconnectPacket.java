package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ReconnectPacket implements Packet {
    public String name;

    public String host;

    public String stats;

    public int port;

    public int gameId;

    public int keyTime;

    public boolean isFromArena;

    public byte[] key;

    public ReconnectPacket() { }

    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.host = in.readUTF();
        this.stats = in.readUTF();
        this.port = in.readInt();
        this.gameId = in.readInt();
        this.keyTime = in.readInt();
        this.isFromArena = in.readBoolean();
        this.key = new byte[in.readShort()];
        in.readFully(key);
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.name);
        out.writeUTF(this.host);
        out.writeUTF(this.stats);
        out.writeInt(this.port);
        out.writeInt(this.gameId);
        out.writeInt(this.keyTime);
        out.writeBoolean(this.isFromArena);
        out.write(this.key);
    }
}
