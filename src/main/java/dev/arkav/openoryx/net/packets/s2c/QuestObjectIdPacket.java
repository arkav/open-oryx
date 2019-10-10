package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class QuestObjectIdPacket implements Packet {
    public int objectId;

    public QuestObjectIdPacket() { }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.objectId);
    }
}
