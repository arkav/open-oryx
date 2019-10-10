package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ServerPlayerShootPacket implements Packet {
    public byte bulletId;

    public int ownerId;

    public int containerType;

    public WorldPosData startingPos;

    public float angle;

    public short damage;

    public ServerPlayerShootPacket() { }

    public void read(DataInput in) throws IOException {
        this.bulletId = in.readByte();
        this.ownerId = in.readInt();
        this.containerType = in.readInt();
        this.startingPos = new WorldPosData();
        this.startingPos.read(in);
        this.angle = in.readFloat();
        this.damage = in.readShort();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeByte(this.bulletId);
        out.writeInt(this.ownerId);
        out.writeInt(this.containerType);
        this.startingPos.write(out);
        out.writeFloat(this.angle);
        out.writeShort(this.damage);
    }
}
