package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ChangeGuildRankPacket implements Packet {
    public String name;
    public int rank;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.rank);
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.rank = in.readInt();
    }
}
