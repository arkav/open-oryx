package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PongPacket implements Packet {
    public int serial;

    public int time;

    public PongPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.serial);
        out.writeInt(this.time);
    }

    public void read(DataInput in) throws IOException {
        this.serial = in.readInt();
        this.time = in.readInt();
    }
}
