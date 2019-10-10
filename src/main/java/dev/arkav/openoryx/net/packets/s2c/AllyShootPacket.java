package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class AllyShootPacket implements Packet {
    public byte bulletId;

    public int ownerId;

    public short containerType;

    public float angle;

    public AllyShootPacket() { }

    public void read(DataInput in) throws IOException {
        this.bulletId = in.readByte();
        this.ownerId = in.readInt();
        this.containerType = in.readShort();
        this.angle = in.readFloat();
    }

    public void write(DataOutput out) throws IOException{
        out.writeByte(bulletId);
        out.writeInt(ownerId);
        out.writeShort(this.containerType);
        out.writeFloat(this.angle);
    }
}
