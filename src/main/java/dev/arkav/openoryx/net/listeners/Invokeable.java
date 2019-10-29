package dev.arkav.openoryx.net.listeners;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.packets.PacketType;

import java.io.IOException;

public interface Invokeable {
    void invoke(Packet p) throws IOException;
    void invoke(ListenerType l) throws Exception;
    default boolean has(PacketType type) {
        return true;
    }
}
