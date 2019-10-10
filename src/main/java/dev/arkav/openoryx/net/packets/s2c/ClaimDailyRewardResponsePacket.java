package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ClaimDailyRewardResponsePacket implements Packet {
    public int itemId;

    public int quantity;

    public int gold;

    public ClaimDailyRewardResponsePacket() { }

    public void read(DataInput in) throws IOException {
        this.itemId = in.readInt();
        this.quantity = in.readInt();
        this.gold = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.itemId);
        out.writeInt(this.quantity);
        out.writeInt(this.gold);
    }
}
