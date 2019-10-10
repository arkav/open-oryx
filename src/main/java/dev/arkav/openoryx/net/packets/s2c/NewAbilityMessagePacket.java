package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class NewAbilityMessagePacket implements Packet {
    public int abilityType;

    public NewAbilityMessagePacket() { }

    public void read(DataInput in) throws IOException {
        this.abilityType = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.abilityType);
    }
}
