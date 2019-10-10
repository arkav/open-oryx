package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class EnemyShootPacket implements Packet {
    public byte bulletId;

    public int ownerId;

    public byte bulletType;

    public WorldPosData startingPos;

    public float angle;

    public short dammage;

    public byte numShots;

    public float angleInc;

    public EnemyShootPacket() { }

    public void read(DataInput in) throws IOException {
        this.bulletId = in.readByte();
        this.ownerId = in.readInt();
        this.bulletType = in.readByte();
        this.startingPos = new WorldPosData();
        this.startingPos.read(in);
        this.angle = in.readFloat();
        this.dammage = in.readShort();
        try {
            this.numShots = in.readByte();
            this.angleInc = in.readFloat();
        } catch (Exception e) {
            this.numShots = 1;
            this.angleInc = 0;
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeByte(this.bulletId);
        out.writeInt(this.ownerId);
        out.writeByte(this.bulletType);
        this.startingPos.write(out);
        out.writeFloat(this.angle);
        out.writeShort(this.dammage);
        if (this.numShots != 1) {
            out.writeByte(this.numShots);
            out.writeFloat(this.angleInc);
        }
    }
}
