package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MapInfoPacket implements Packet {
    public int width;
    public int height;

    public String name;
    public String displayName;

    public int difficulty;

    public int fp;

    public int background;

    public boolean allowPlayerTeleport;

    public boolean showDisplays;

    public String[] clientXML = new String[0];
    public String[] extraXML = new String[0];

    public MapInfoPacket() { }

    public void read(DataInput in) throws IOException {
        this.width = in.readInt();
        this.height = in.readInt();
        this.name = in.readUTF();
        this.displayName = in.readUTF();
        this.fp = in.readInt();
        this.background = in.readInt();
        this.difficulty = in.readInt();
        this.allowPlayerTeleport = in.readBoolean();
        this.showDisplays = in.readBoolean();
        this.clientXML = new String[in.readShort()];
        for (int i = 0; i < clientXML.length; i++) {
            byte[] bytes = new byte[in.readInt()];
            in.readFully(bytes);
            this.clientXML[i] = new String(bytes);
        }
        this.extraXML = new String[in.readShort()];
        for(int i = 0; i < this.extraXML.length; i++) {
            byte[] bytes = new byte[in.readInt()];
            in.readFully(bytes);
            this.extraXML[i] = new String(bytes);
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeInt(this.width);
        out.writeInt(this.height);
        out.writeUTF(this.name);
        out.writeUTF(this.displayName);
        out.writeInt(this.fp);
        out.writeInt(this.background);
        out.writeInt(this.difficulty);
        out.writeBoolean(this.allowPlayerTeleport);
        out.writeBoolean(this.showDisplays);
        out.writeShort((short)this.clientXML.length);
        for (String xml : this.clientXML) {
            byte[] bytes = xml.getBytes();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        out.writeShort((short)this.extraXML.length);
        for(String xml : this.extraXML) {
            byte[] bytes = xml.getBytes();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
    }
}
