package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ChatHelloPacket implements Packet {
    public String accountId;
    public String token;

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.accountId);
        out.writeUTF(this.token);
    }

    public void read(DataInput in) throws IOException {
        this.accountId = in.readUTF();
        this.token = in.readUTF();
    }
}
