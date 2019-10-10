package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShowEffectPacket implements Packet {
    public byte effectType;

    public int targetObjectId;

    public WorldPosData pos1;

    public WorldPosData pos2;

    public int color;

    public float duration;

    public ShowEffectPacket() { }

    public void read(DataInput in) throws IOException {
        this.effectType = in.readByte();
        this.targetObjectId = in.readInt();
        this.pos1 = new WorldPosData();
        this.pos1.read(in);
        this.pos2 = new WorldPosData();
        this.pos2.read(in);
        this.color = in.readInt();
        this.duration = in.readFloat();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeByte(this.effectType);
        out.writeInt(this.targetObjectId);
        this.pos1.write(out);
        this.pos2.write(out);
        out.writeInt(this.color);
        out.writeFloat(this.duration);
    }
}
