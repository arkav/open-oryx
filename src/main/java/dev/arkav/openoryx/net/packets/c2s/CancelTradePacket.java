package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CancelTradePacket implements Packet {
    public CancelTradePacket() { }

    public void read(DataInput in) throws IOException { }
    public void write(DataOutput out) throws IOException { }
}
