package dev.arkav.openoryx.net.packets.s2c;

import dev.arkav.openoryx.net.data.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings({"ALL", "WeakerAccess"})
public class AccountListPacket implements Packet {

    public int accountListId;

    public String[] accountIds;

    public int lockAction;

    public AccountListPacket() {
        this.accountIds = new String[0];
    }

    public void read(DataInput in) throws IOException {
        this.accountListId = in.readInt();
        short accountIdsLen = in.readShort();
        this.accountIds = new String[accountIdsLen];
        for(short i = 0; i < accountIdsLen; i++) {
            this.accountIds[i] = in.readUTF();
        }
        this.lockAction = in.readShort();
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.accountListId);
        out.writeShort((short)this.accountIds.length);
        for (String id : this.accountIds) {
            out.writeUTF(id);
        }
        out.writeInt(this.lockAction);
    }
}
