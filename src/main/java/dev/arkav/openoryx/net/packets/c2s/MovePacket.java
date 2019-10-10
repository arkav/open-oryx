package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.MoveRecord;
import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MovePacket implements Packet {
    public int tickId;

    public int time;

    public WorldPosData worldPos;

    public MoveRecord[] moveRecords;

    public MovePacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.tickId);
        out.writeInt(this.time);
        this.worldPos.write(out);
        out.writeShort(this.moveRecords.length);
        for(MoveRecord moveRecord : this.moveRecords) {
            moveRecord.write(out);
        }
    }

    public void read(DataInput in) throws IOException {
        in.readInt();
        in.readInt();
        this.worldPos = new WorldPosData();
        this.worldPos.read(in);
        this.moveRecords = new MoveRecord[in.readShort()];
        for(short i = 0; i < this.moveRecords.length; i++) {
            this.moveRecords[i] = new MoveRecord();
            this.moveRecords[i].read(in);
        }
    }
}
