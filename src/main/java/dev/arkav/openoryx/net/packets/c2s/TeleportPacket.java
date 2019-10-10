package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TeleportPacket implements Packet {
    public int objectId;

    public TeleportPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.objectId);
    }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
    }
}
