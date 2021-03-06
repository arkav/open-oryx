package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SlotObjectData implements DataPacket {
    public int objectId;
    public int slotId;
    public int objectType;

    public SlotObjectData() { }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.slotId = in.readUnsignedByte();
        this.objectType = in.readInt();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.objectId);
        out.writeByte(this.slotId);
        out.writeInt(this.objectType);
    }
}
