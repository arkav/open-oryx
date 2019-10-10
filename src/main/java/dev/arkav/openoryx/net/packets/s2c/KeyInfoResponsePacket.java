package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class KeyInfoResponsePacket implements Packet {
    public String name;

    public String description;

    public String creator;

    public KeyInfoResponsePacket() { }

    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.description = in.readUTF();
        this.creator = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.name);
        out.writeUTF(this.description);
        out.writeUTF(this.creator);
    }
}
