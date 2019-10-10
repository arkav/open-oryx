package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PlayerHitPacket implements Packet {
    public byte bulletId;

    public int objectId;

    public PlayerHitPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeByte(this.bulletId);
        out.writeInt(this.objectId);
    }

    public void read(DataInput in) throws IOException {
        this.bulletId = in.readByte();
        this.objectId = in.readInt();
    }
}
