package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ClientStatPacket implements Packet {
    public String name;

    public int value;

    public ClientStatPacket() { }

    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.value = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.name);
        out.writeInt(this.value);
    }
}
