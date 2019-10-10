package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SetConditionPacket implements Packet {
    public byte effect;

    public float duration;

    public void write(DataOutput out) throws IOException {
        out.writeByte(this.effect);
        out.writeFloat(this.duration);
    }

    public void read(DataInput in) throws IOException {
        this.effect = in.readByte();
        this.duration = in.readFloat();
    }
}
