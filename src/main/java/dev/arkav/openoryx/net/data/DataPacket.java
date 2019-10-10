package dev.arkav.openoryx.net.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface DataPacket {
    void read(DataInput in) throws IOException;

    void write(DataOutput out) throws IOException;
}
