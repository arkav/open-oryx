package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.ObjectStatusData;
import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class NewTickPacket implements Packet {
    public int tickId;

    public int tickTime;

    public ObjectStatusData[] statuses;

    public void read(DataInput in) throws IOException {
        this.tickId = in.readInt();
        this.tickTime = in.readInt();
        short statusesLen = in.readShort();
        this.statuses = new ObjectStatusData[statusesLen];
        for(short i = 0; i < statusesLen; i++) {
            ObjectStatusData d = new ObjectStatusData();
            d.read(in);
            this.statuses[i] = d;
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.tickId);
        out.writeInt(this.tickTime);
        for (ObjectStatusData status : this.statuses) {
            status.write(out);
        }
    }
}
