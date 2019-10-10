package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class FailurePacket implements Packet {
    public int errorId;

    public String errorDescription;

    public FailurePacket() { }

    public void read(DataInput in) throws IOException {
        this.errorId = in.readInt();
        this.errorDescription = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.errorId);
        out.writeUTF(this.errorDescription);
    }
}
