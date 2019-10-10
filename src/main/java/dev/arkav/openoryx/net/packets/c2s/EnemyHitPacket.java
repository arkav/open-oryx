package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EnemyHitPacket implements Packet {
    public int time;
    public byte bulletId;
    public int targetId;
    public boolean kill;

    public EnemyHitPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        out.writeByte(this.bulletId);
        out.writeInt(this.targetId);
        out.writeBoolean(this.kill);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.bulletId = in.readByte();
        this.targetId = in.readInt();
        this.kill = in.readBoolean();
    }
}
