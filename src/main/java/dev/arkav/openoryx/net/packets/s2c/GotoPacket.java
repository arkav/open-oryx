package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GotoPacket implements Packet {
    public int objectId;

    public WorldPosData pos;

    public GotoPacket() { }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.pos = new WorldPosData();
        this.pos.read(in);
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.objectId);
        this.pos.write(out);
    }
}
