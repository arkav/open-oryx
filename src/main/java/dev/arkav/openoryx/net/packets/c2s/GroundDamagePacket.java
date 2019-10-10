package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GroundDamagePacket implements Packet {
    public int time;

    public WorldPosData pos;

    public GroundDamagePacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        this.pos.write(out);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.pos = new WorldPosData();
        this.pos.read(in);
    }

    public static GroundDamagePacket createDefault() {
        GroundDamagePacket p = new GroundDamagePacket();
        p.pos = new WorldPosData();
        return p;
    }
}
