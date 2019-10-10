package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class KeyInfoRequestPacket implements Packet {
    @SuppressWarnings("WeakerAccess")
    public int itemType;

    public KeyInfoRequestPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.itemType);
    }

    public void read(DataInput in) throws IOException {
        this.itemType = in.readInt();
    }
}
