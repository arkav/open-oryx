package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CreateSuccessPacket implements Packet {
    public int objectId;

    public int charId;

    public CreateSuccessPacket() { }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.charId = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.objectId);
        out.writeInt(this.charId);
    }
}
