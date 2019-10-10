package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class RealmHeroLeftMsgPacket implements Packet {
    public int herosLeft;
    public void read(DataInput in) throws IOException {
        this.herosLeft = in.readInt();
    }

    public void write(DataOutput out) throws IOException {
        out.write(this.herosLeft);
    }
}
