package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LoadPacket implements Packet {
    public int charId;
    public boolean isFromArena;
    public boolean isChallenger;

    public LoadPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.charId);
        out.writeBoolean(this.isFromArena);
        out.writeBoolean(this.isChallenger);
    }

    public void read(DataInput in) throws IOException {
        this.charId = in.readInt();
        this.isFromArena = in.readBoolean();
        this.isChallenger = in.readBoolean();
    }
}
