package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ChangeTradePacket implements Packet {
    public boolean[] offer;

    public ChangeTradePacket() { }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeShort(this.offer.length);
        for(boolean o : this.offer) {
            out.writeBoolean(o);
        }
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.offer = new boolean[in.readShort()];
        for(short i = 0; i < this.offer.length; i++) {
            this.offer[i] = in.readBoolean();
        }
    }

    public static void createDefault() {
        ChangeTradePacket p = new ChangeTradePacket();
        p.offer = new boolean[12];
    }
}
