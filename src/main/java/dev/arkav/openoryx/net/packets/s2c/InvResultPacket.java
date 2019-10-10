package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class InvResultPacket implements Packet {
    public int result;

    public InvResultPacket() { }

    public void read(DataInput in) throws IOException {
        this.result = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.result);
    }
}
