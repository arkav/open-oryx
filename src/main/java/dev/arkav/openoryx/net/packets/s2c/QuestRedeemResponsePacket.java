package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class QuestRedeemResponsePacket implements Packet {
    public boolean ok;

    public String message;

    public void read(DataInput in) throws IOException {
        this.ok = in.readBoolean();
        this.message = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeBoolean(this.ok);
        out.writeUTF(this.message);
    }
}
