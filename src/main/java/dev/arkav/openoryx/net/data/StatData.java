package dev.arkav.openoryx.net.data;

import dev.arkav.openoryx.net.packets.StatType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StatData implements DataPacket {
    public byte statType = 0;

    public int statValue;

    public String stringStatValue;

    public StatData() { }

    public void read(DataInput in) throws IOException {
        this.statType = in.readByte();
        if(this.isStringStat()) {
            this.stringStatValue = in.readUTF();
        } else {
            this.statValue = in.readInt();
        }
    }

    public void write(DataOutput out) throws IOException  {
        out.writeByte(this.statType);
        if(this.isStringStat()) {
            out.writeUTF(this.stringStatValue);
        } else {
            out.writeInt(this.statValue);
        }
    }

    private boolean isStringStat() {
        switch (this.statType) {
            case StatType.NAME_STAT: return true;
            case StatType.GUILD_NAME_STAT: return true;
            case StatType.PET_NAME_STAT: return true;
            case StatType.ACCOUNT_ID_STAT: return true;
            case StatType.OWNER_ACCOUNT_ID_STAT: return true;
            default: return false;
        }
    }
}
