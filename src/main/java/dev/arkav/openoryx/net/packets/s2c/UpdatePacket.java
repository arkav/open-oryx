package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.GroundTileData;
import dev.arkav.openoryx.net.data.ObjectData;
import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UpdatePacket implements Packet {
    public GroundTileData[] tiles;

    public ObjectData[] newObjects;

    public int[] drops;

    public UpdatePacket() {
        this.tiles = new GroundTileData[0];
        this.newObjects = new ObjectData[0];
        this.drops = new int[0];
    }

    public void read(DataInput in) throws IOException {
        short tilesLen = in.readShort();
        this.tiles = new GroundTileData[tilesLen];
        for(short i = 0; i < tilesLen; i++) {
            GroundTileData tile = new GroundTileData();
            tile.read(in);
            this.tiles[i] = tile;
        }

        short newObjectsLen = in.readShort();
        this.newObjects = new ObjectData[newObjectsLen];
        for(short i = 0; i < newObjectsLen; i++) {
            ObjectData obj = new ObjectData();
            obj.read(in);
            this.newObjects[i] = obj;
        }

        short dropsLen = in.readShort();
        this.drops = new int[dropsLen];
        for(short i = 0; i < dropsLen; i++) {
            this.drops[i] = in.readInt();
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeShort(this.tiles.length);
        for(GroundTileData tile : this.tiles) {
            tile.write(out);
        }

        out.writeShort(this.newObjects.length);
        for(ObjectData obj : this.newObjects) {
            obj.write(out);
        }

        out.writeShort(this.drops.length);
        for(int drop : this.drops) {
            out.writeInt(drop);
        }
    }
}
