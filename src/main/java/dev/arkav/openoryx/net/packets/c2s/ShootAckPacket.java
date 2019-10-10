package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShootAckPacket implements Packet {
    public int time;

    public ShootAckPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
    }
}
