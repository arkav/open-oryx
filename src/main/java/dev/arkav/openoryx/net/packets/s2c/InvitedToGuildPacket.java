package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class InvitedToGuildPacket implements Packet {
    public String name;

    public String guildName;

    public InvitedToGuildPacket() { }

    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.guildName = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.name);
        out.writeUTF(this.guildName);
    }
}
