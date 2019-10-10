package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("ALL")
public class DeathPacket implements Packet {
    public String accountId;

    public int charId;

    public String killedBy;

    public int zombieType;

    public int zombieId;

    public boolean isZombie;

    public DeathPacket() { }

    public void read(DataInput in) throws IOException {
        this.accountId = in.readUTF();
        this.charId = in.readInt();
        this.killedBy = in.readUTF();
        this.zombieType = in.readInt();
        this.zombieId = in.readInt();
        this.isZombie = this.zombieId != -1;
    }

    public void write(DataOutput out) throws IOException  {
        out.writeUTF(this.accountId);
        out.writeInt(this.charId);
        out.writeUTF(this.killedBy);
        out.writeInt(this.zombieType);
        out.writeInt(this.zombieId);
    }
}
