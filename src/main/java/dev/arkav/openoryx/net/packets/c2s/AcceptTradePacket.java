package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AcceptTradePacket implements Packet {

    public boolean[] clientOffer;
    public boolean[] partnerOffer;

    public AcceptTradePacket () { }

    public void write(DataOutput out) throws IOException {
        out.writeShort(this.clientOffer.length);
        for(boolean o : this.clientOffer) {
            out.writeBoolean(o);
        }
        out.writeShort(this.partnerOffer.length);
        for(boolean o : this.partnerOffer) {
            out.writeBoolean(o);
        }
    }

    public void read(DataInput in) throws IOException {
        this.clientOffer = new boolean[in.readShort()];
        for (short i = 0; i < this.clientOffer.length; i++) {
            this.clientOffer[i] = in.readBoolean();
        }
        this.partnerOffer = new boolean[in.readShort()];
        for(short i = 0; i < this.partnerOffer.length; i++) {
            this.partnerOffer[i] = in.readBoolean();
        }
    }

    public static AcceptTradePacket createDefault() {
        AcceptTradePacket p = new AcceptTradePacket();
        p.clientOffer = new boolean[12];
        p.partnerOffer = new boolean[12];
        return p;
    }
}
