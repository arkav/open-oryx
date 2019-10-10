package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class NameResultPacket implements Packet {
    public boolean success;

    public String errorText;

    public NameResultPacket() { }

    public void read(DataInput in) throws IOException {
        this.success = in.readBoolean();
        this.errorText = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeBoolean(this.success);
        out.writeUTF(this.errorText);
    }
}
