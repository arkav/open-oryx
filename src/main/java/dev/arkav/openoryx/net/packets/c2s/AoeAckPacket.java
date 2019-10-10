package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AoeAckPacket implements Packet {
    public int time;
    public WorldPosData pos;

    public AoeAckPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(time);
        this.pos.write(out);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.pos = new WorldPosData();
        this.pos.read(in);
    }
}
