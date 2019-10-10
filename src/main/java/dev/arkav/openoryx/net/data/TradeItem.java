package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TradeItem implements DataPacket {
    public int item;

    public int slotType;

    public boolean tradeable;

    public boolean included;

    public void read(DataInput in) throws IOException  {
        this.item = in.readInt();
        this.slotType = in.readInt();
        this.tradeable = in.readBoolean();
        this.included = in.readBoolean();
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.item);
        out.writeInt(this.slotType);
        out.writeBoolean(this.tradeable);
        out.writeBoolean(this.included);
    }
}
