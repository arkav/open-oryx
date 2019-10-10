package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class OtherHitPacket implements Packet {
    public int time;

    public byte bulletId;

    public int objectId;

    public int targetId;

    public OtherHitPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        out.writeByte(this.bulletId);
        out.writeInt(this.objectId);
        out.writeInt(this.targetId);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.bulletId = in.readByte();
        this.objectId = in.readInt();
        this.targetId = in.readInt();
    }
}
