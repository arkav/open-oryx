package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class AoePacket implements Packet {


    public WorldPosData pos;

    public float radius;

    public short dammage;

    public byte effect;

    public float durration;

    public short origType;

    public int color;

    public boolean armorPierce;

    public AoePacket() {
        this.pos = new WorldPosData();
    }

    public void read(DataInput in) throws IOException {
        this.pos = new WorldPosData();
        this.pos.read(in);
        this.radius = in.readFloat();
        this.dammage = in.readShort();
        this.effect = in.readByte();
        this.durration = in.readFloat();
        this.origType = in.readShort();
        this.color = in.readInt();
        this.armorPierce = in.readBoolean();
    }

    public void write(DataOutput out) throws IOException {
        this.pos.write(out);
        out.writeFloat(this.radius);
        out.writeShort(this.dammage);
        out.writeByte(this.effect);
        out.writeFloat(this.durration);
        out.writeShort(this.origType);
        out.writeInt(this.color);
        out.writeBoolean(this.armorPierce);
    }
}
