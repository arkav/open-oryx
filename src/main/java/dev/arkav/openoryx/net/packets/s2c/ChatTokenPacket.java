package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ChatTokenPacket implements Packet {
    public String token;
    public String host;
    public int port;

    public ChatTokenPacket() { }

    public void read(DataInput in) throws IOException {
        this.token = in.readUTF();
        this.host = in.readUTF();
        this.port = in.readInt();
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.token);
        out.writeUTF(this.host);
        out.writeInt(this.port);
    }
}
