package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class EditAccountListPacket implements Packet {
    public int accountListId;
    public boolean add;
    public int objectId;

    public EditAccountListPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.accountListId);
        out.writeBoolean(this.add);
        out.writeInt(this.objectId);
    }

    public void read(DataInput in) throws IOException {
        this.accountListId = in.readInt();
        this.add = in.readBoolean();
        this.objectId = in.readInt();
    }
}
