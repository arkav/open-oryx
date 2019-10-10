package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CreatePacket implements Packet {
    public short classType;
    public short skinType;
    public boolean isChallenger;

    public CreatePacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeShort(this.classType);
        out.writeShort(this.skinType);
        out.writeBoolean(this.isChallenger);
    }

    public void read(DataInput in) throws IOException {
        this.classType = in.readShort();
        this.skinType = in.readShort();
        this.isChallenger = in.readBoolean();
    }
}
