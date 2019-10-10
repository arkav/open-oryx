package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class JoinGuildPacket implements Packet {
    public String guildName;

    public JoinGuildPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.guildName);
    }

    public void read(DataInput in) throws IOException {
        this.guildName = in.readUTF();
    }
}
