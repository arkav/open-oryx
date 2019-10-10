package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MoveRecord implements DataPacket {
    public int time;

    public float x;

    public float y;

    public MoveRecord() { }

    public MoveRecord(int time, float x, float y) {
        this.time = time;
        this.x = x;
        this.y = y;
    }

    public void read(DataInput in) throws IOException {
        this.time = in.readInt();
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.time);
        out.writeFloat(this.x);
        out.writeFloat(this.y);
    }

    public MoveRecord clone() {
        return new MoveRecord(this.time, this.x, this.y);
    }
}
