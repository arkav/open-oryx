package dev.arkav.openoryx.net.listeners;

import dev.arkav.openoryx.net.data.Packet;

import java.io.IOException;

public interface PacketListener<T extends Packet> {
    void call(T p) throws IOException;
}
