package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class NotificationPacket implements Packet {
    public int objectId;

    public String message;

    public int color;

    public NotificationPacket() { }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.message = in.readUTF();
        this.color = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.objectId);
        out.writeUTF(this.message);
        out.writeInt(this.color);
    }
}
