package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class PasswordPromptPacket implements Packet {
    public int cleanPasswordStatus;

    public PasswordPromptPacket() { }

    public void read(DataInput in) throws IOException {
        this.cleanPasswordStatus = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.cleanPasswordStatus);
    }
}
