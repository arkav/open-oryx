package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TradeChangedPacket implements Packet {
    public boolean[] offer;

    public TradeChangedPacket() {
        this.offer = new boolean[12];
    }

    public void read(DataInput in) throws IOException {
        short offerLen = in.readShort();
        this.offer = new boolean[offerLen];
        for(short i = 0; i < offerLen; i++) {
            this.offer[i] = in.readBoolean();
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeShort((short)this.offer.length);
        for(boolean o : this.offer) {
            out.writeBoolean(o);
        }
    }
}
