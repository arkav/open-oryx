package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class ClaimDailyRewardMessagePacket implements Packet {
    public String claimKey;
    public String claimType;

    public ClaimDailyRewardMessagePacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.claimKey);
        out.writeUTF(this.claimType);
    }

    public void read(DataInput in) throws IOException {
        this.claimKey = in.readUTF();
        this.claimType = in.readUTF();
    }
}
