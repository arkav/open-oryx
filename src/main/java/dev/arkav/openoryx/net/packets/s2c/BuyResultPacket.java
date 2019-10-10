package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class BuyResultPacket implements Packet {
    public int result;

    public String resultString;

    public BuyResultPacket() { }

    public void read(DataInput in) throws IOException {
        this.result = in.readInt();
        this.resultString = in.readUTF();
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.result);
        out.writeUTF(this.resultString);
    }
}
