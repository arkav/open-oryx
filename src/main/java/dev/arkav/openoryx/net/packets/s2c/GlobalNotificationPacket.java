package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class GlobalNotificationPacket implements Packet {
    public int notificationType;

    public String text;

    public GlobalNotificationPacket() { }

    public void read(DataInput in) throws IOException {
        this.notificationType = in.readInt();
        this.text = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.notificationType);
        out.writeUTF(this.text);
    }
}
