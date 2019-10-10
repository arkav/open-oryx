package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GroundTileData implements DataPacket {
    public short x;

    public short y;

    public short type;

    public void read(DataInput in) throws IOException {
        this.x = in.readShort();
        this.y = in.readShort();
        this.type = in.readShort();
    }

    public void write(DataOutput out) throws IOException {
        out.writeShort(this.x);
        out.writeShort(this.y);
        out.writeShort(this.type);
    }
}
