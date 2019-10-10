package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class BuyPacket implements Packet {
    public int objectId;
    public int quantity;

    public BuyPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.objectId);
        out.writeInt(this.quantity);
    }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.quantity = in.readInt();
    }
}
