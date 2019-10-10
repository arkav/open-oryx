package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UpdateAckPacket implements Packet {
    public UpdateAckPacket() { }

    public void write(DataOutput out) throws IOException { }

    public void read(DataInput in) throws IOException { }
}
