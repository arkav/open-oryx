package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TextPacket implements Packet {
    public String name;

    public int objectId;

    public int numStars;

    public byte bubbleTime;

    public String recipient;

    public String text;

    public String cleanText;

    public boolean isSupporter;

    public int starBackground;

    public void read(DataInput in) throws IOException {
        this.name = in.readUTF();
        this.objectId = in.readInt();
        this.numStars = in.readInt();
        this.bubbleTime = in.readByte();
        this.recipient = in.readUTF();
        this.text = in.readUTF();
        this.cleanText = in.readUTF();
        this.isSupporter = in.readBoolean();
        this.starBackground = in.readInt();
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.name);
        out.writeInt(this.objectId);
        out.writeInt(this.numStars);
        out.writeByte(this.bubbleTime);
        out.writeUTF(this.recipient);
        out.writeUTF(this.text);
        out.writeUTF(this.cleanText);
        out.writeBoolean(this.isSupporter);
        out.writeInt(this.starBackground);
    }

    public static TextPacket createDefault() {
        TextPacket p = new TextPacket();
        p.name = "";
        p.objectId = 0;
        p.numStars = -1;
        p.bubbleTime = 0;
        p.recipient = "";
        p.text = "";
        p.cleanText = "";
        p.isSupporter = false;
        p.starBackground = -1;
        return p;
    }
}
