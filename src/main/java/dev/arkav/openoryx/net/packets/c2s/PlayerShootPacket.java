package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class PlayerShootPacket implements Packet {
    public int time;

    public byte bulletId;

    public short containerType;

    public WorldPosData startingPos;

    public float angle;

    public PlayerShootPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        out.writeByte(this.bulletId);
        out.writeShort(this.containerType);
        this.startingPos.write(out);
        out.writeFloat(this.angle);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.bulletId = in.readByte();
        this.containerType = in.readShort();
        this.startingPos = new WorldPosData();
        this.startingPos.read(in);
        this.angle = in.readFloat();
    }
}
