package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class GuildResultPacket implements Packet {
    public boolean success;

    public String lineBuilderJSON;

    public GuildResultPacket() { }

    public void read(DataInput in) throws IOException {
        this.success = in.readBoolean();
        this.lineBuilderJSON = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeBoolean(this.success);
        out.writeUTF(this.lineBuilderJSON);
    }
}
