package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.SlotObjectData;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UseItemPacket implements Packet {
    public int time;

    public SlotObjectData slotObject;

    public WorldPosData pos;

    public byte useType;

    public UseItemPacket() { }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        this.slotObject.write(out);
        this.pos.write(out);
        out.writeByte(this.useType);
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.slotObject = new SlotObjectData();
        this.slotObject.read(in);
        this.pos = new WorldPosData();
        this.pos.read(in);
        this.useType = in.readByte();
    }
}
