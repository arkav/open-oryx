package dev.arkav.openoryx.net.listeners;

import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.packets.PacketType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListenerStore {
    private Map<PacketType, ArrayList<PacketListener<? extends Packet>>> packetMap;
    private Map<ListenerType, ArrayList<IOListener>> ioMap;

    public ListenerStore() {
        this.packetMap = new HashMap<>();
        this.ioMap = new HashMap<>();
    }

    public void hook(PacketType type, PacketListener<? extends Packet> method) {
        if(!this.packetMap.containsKey(type)) {
            this.packetMap.put(type, new ArrayList<>());
        }
        this.packetMap.get(type).add(method);
    }

    public void hook(ListenerType type, IOListener method) {
        if(!this.ioMap.containsKey(type)) {
            this.ioMap.put(type, new ArrayList<>());
        }
        this.ioMap.get(type).add(method);
    }

    public void invoke(ListenerType type) throws Exception {
        if (this.ioMap.containsKey(type)) {
            for (IOListener method : this.ioMap.get(type)) {
                method.call();
            }
        }
    }

    public void invoke(Packet p) throws IOException {
        if (this.packetMap.containsKey(PacketType.typeOf(p))) {
            for (PacketListener method : this.packetMap.get(PacketType.typeOf(p))) {
                //noinspection unchecked
                method.call(p);
            }
        }
    }

    public void removeListeners(PacketType type) {
        if(!this.packetMap.containsKey(type)) {
            this.packetMap.put(type, new ArrayList<>());
        }
        this.packetMap.get(type).clear();
    }

    public boolean has(PacketType pt) {
        return this.packetMap.containsKey(pt);
    }
}
