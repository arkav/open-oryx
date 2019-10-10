package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ReskinPacket implements Packet {
    public int skinId;

    public ReskinPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.skinId);
    }

    public void read(DataInput in) throws IOException {
        this.skinId = in.readInt();
    }
}