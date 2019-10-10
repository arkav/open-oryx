package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ObjectStatusData implements DataPacket {
    public int objectId;

    public WorldPosData pos;

    public StatData[] stats;

    public ObjectStatusData() {
        this.pos = new WorldPosData();
    }

    public void read(DataInput in) throws IOException {
        this.objectId = in.readInt();
        this.pos = new WorldPosData();
        this.pos.read(in);
        short statLen = in.readShort();
        this.stats = new StatData[statLen];
        for (short i = 0; i < statLen; i++) {
            StatData sd = new StatData();
            sd.read(in);
            this.stats[i] = sd;
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.objectId);
        this.pos.write(out);
        out.writeShort(this.stats.length);
        for (StatData stat : this.stats) {
            stat.write(out);
        }
    }
}
