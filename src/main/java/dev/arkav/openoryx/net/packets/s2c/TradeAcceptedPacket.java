package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TradeAcceptedPacket implements Packet {
    public boolean[] clientOffer;

    public boolean[] partnerOffer;

    public TradeAcceptedPacket() {
        this.clientOffer = new boolean[12];
        this.partnerOffer = new boolean[12];
    }

    public void read(DataInput in) throws IOException {
        short clientOfferLen = in.readShort();
        this.clientOffer = new boolean[clientOfferLen];
        for(short i = 0; i < clientOfferLen; i++) {
            this.clientOffer[i] = in.readBoolean();
        }
        short partnerOfferLen = in.readShort();
        this.partnerOffer = new boolean[partnerOfferLen];
        for(short i = 0; i < partnerOfferLen; i++) {
            this.partnerOffer[i] = in.readBoolean();
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeShort((short)this.clientOffer.length);
        for(boolean o : this.clientOffer) {
            out.writeBoolean(o);
        }
        out.writeShort((short)this.partnerOffer.length);
        for(boolean o : this.partnerOffer) {
            out.writeBoolean(o);
        }
    }
}
