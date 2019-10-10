package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ObjectData implements DataPacket {
    public short objectType;

    public ObjectStatusData status;

    public void read(DataInput in) throws IOException {
        this.objectType = in.readShort();
        this.status = new ObjectStatusData();
        this.status.read(in);
    }

    public void write(DataOutput out) throws IOException {
        out.writeShort(this.objectType);
        this.status.write(out);
    }
}
