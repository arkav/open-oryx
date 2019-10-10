package dev.arkav.openoryx.net.data;

import dev.arkav.openoryx.game.models.Vector2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class WorldPosData extends Vector2 implements DataPacket {

    public WorldPosData() {
        super(0, 0);
    }
    public WorldPosData(float x, float y) {
        super(x, y);
    }

    public void read(DataInput in) throws IOException {
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeFloat(this.x);
        out.writeFloat(this.y);
    }

    public WorldPosData clone() {
        return new WorldPosData(this.x, this.y);
    }

    public double distanceTo(float x, float y) {
        return Math.sqrt(Math.pow(this.x -x, 2) + Math.pow(this.y - y, 2));
    }
}
