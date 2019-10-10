package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TradeDonePacket implements Packet {
    public int code;

    public String message;

    public TradeDonePacket() { }

    public void read(DataInput in) throws IOException {
        this.code = in.readInt();
        this.message = in.readUTF();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.code);
        out.writeUTF(this.message);
    }
}
