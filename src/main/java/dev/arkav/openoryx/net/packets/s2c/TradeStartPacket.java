package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.TradeItem;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TradeStartPacket implements Packet {
    public TradeItem[] clientItems;

    public String partnerName;

    public TradeItem[] partnerItems;

    public TradeStartPacket() {
        this.clientItems = new TradeItem[12];
        this.partnerItems = new TradeItem[12];
    }

    public void read(DataInput in) throws IOException {
        short clientItemsLen = in.readShort();
        this.clientItems = new TradeItem[clientItemsLen];
        for(short i = 0; i < clientItemsLen; i++) {
            TradeItem item = new TradeItem();
            item.read(in);
            this.clientItems[i] = item;
        }
        this.partnerName = in.readUTF();
        short partnerItemsLen = in.readShort();
        this.partnerItems = new TradeItem[partnerItemsLen];
        for(short i = 0; i < partnerItemsLen; i++) {
            TradeItem item = new TradeItem();
            item.read(in);
            this.partnerItems[i] = item;
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeShort((short)this.clientItems.length);
        for(TradeItem item : this.clientItems) {
            item.write(out);
        }
        out.writeUTF(this.partnerName);
        out.writeShort((short)this.partnerItems.length);
        for(TradeItem item : this.partnerItems) {
            item.write(out);
        }
    }
}
