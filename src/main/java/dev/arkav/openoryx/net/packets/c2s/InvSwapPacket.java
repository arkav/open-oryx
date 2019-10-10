package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.SlotObjectData;
import dev.arkav.openoryx.net.data.WorldPosData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class InvSwapPacket implements Packet {
    public int time;
    public WorldPosData pos;
    public SlotObjectData slotObject1;
    public SlotObjectData slotObject2;

    public InvSwapPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        this.pos.write(out);
        this.slotObject1.write(out);
        this.slotObject2.write(out);
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.pos = new WorldPosData();
        pos.read(in);
        this.slotObject1 = new SlotObjectData();
        this.slotObject1.read(in);
        this.slotObject2 = new SlotObjectData();
        this.slotObject2.read(in);
    }
}
