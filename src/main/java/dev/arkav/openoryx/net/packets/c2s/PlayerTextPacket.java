package dev.arkav.openoryx.net.packets.c2s;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class PlayerTextPacket implements Packet {
    public String text;

    public PlayerTextPacket() { }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.text);
    }

    public void read(DataInput in) throws IOException {
        this.text = in.readUTF();
    }
}
