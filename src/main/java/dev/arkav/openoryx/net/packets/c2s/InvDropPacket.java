package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.data.SlotObjectData;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class InvDropPacket implements Packet {
    public SlotObjectData slotObject;

    public InvDropPacket() { }

    public void write(DataOutput out) throws IOException {
        this.slotObject.write(out);
    }

    public void read(DataInput in) throws IOException {
        this.slotObject = new SlotObjectData();
        this.slotObject.read(in);
    }
}
