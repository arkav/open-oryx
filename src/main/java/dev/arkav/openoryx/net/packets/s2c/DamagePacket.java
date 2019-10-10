package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class DamagePacket implements Packet {
    public int targetId;

    public byte[] effects;

    public short dammageAmount;

    public boolean kill;

    public boolean armorPierce;

    public byte bulletId;

    public int objectId;

    public DamagePacket() { }

    public void read(DataInput in) throws IOException {
        this.targetId = in.readInt();
        int effectsLen = in.readByte();
        this.effects = new byte[effectsLen];
        for (int i = 0; i< effectsLen; i++) {
            this.effects[i] = in.readByte();
        }
        this.dammageAmount = in.readShort();
        this.kill = in.readBoolean();
        this.armorPierce = in.readBoolean();
        this.bulletId = in.readByte();
        this.objectId = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.targetId);
        out.writeByte((byte)this.effects.length);
        for (byte effect : this.effects) {
            out.writeByte(effect);
        }
        out.writeBoolean(this.kill);
        out.writeBoolean(this.armorPierce);
        out.writeByte(this.bulletId);
        out.writeInt(this.objectId);
    }
}
