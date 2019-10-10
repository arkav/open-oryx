package dev.arkav.openoryx.net;

import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.net.crypto.HexUtil;
import dev.arkav.openoryx.net.crypto.RC4;
import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.listeners.ListenerStore;
import dev.arkav.openoryx.net.listeners.ListenerType;
import dev.arkav.openoryx.net.packets.PacketType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ProxyIO implements Runnable {
    private Socket socket;

    private DataInputStream input;
    private DataOutputStream output;

    private boolean connected;

    private final String RC4_INCOMING_KEY;
    private final String RC4_OUTGOING_KEY;

    private RC4 inRC4;
    private RC4 outRC4;

    private ListenerStore listeners;

    private ProxyIO partner;

    private ArrayList<PacketType> blocked;

    public ProxyIO(Socket socket, ListenerStore listeners, final String RC4_INCOMING_KEY, final String RC4_OUTGOING_KEY) throws IOException {
        this.listeners = listeners;
        this.RC4_INCOMING_KEY = RC4_INCOMING_KEY;
        this.RC4_OUTGOING_KEY = RC4_OUTGOING_KEY;

        this.blocked = new ArrayList<>();
        this.connect(socket);
    }

    private void connect(Socket socket) throws IOException {
        this.socket = socket;

        this.input = new DataInputStream(this.socket.getInputStream());
        this.output = new DataOutputStream(this.socket.getOutputStream());

        this.inRC4 = new RC4(HexUtil.hexStringToByteArray(this.RC4_OUTGOING_KEY));
        this.outRC4 = new RC4(HexUtil.hexStringToByteArray(this.RC4_INCOMING_KEY));
    }

    public void start() {
        this.connected = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (this.connected) {
            try {
                if (this.input.available() == -1) throw new EOFException("End of stream!");

                int bufSize = this.input.readInt() - 5;
                byte id = this.input.readByte();
                PacketType type = PacketMapper.get(id);
                byte[] buf = new byte[bufSize];
                this.input.readFully(buf);
                byte[] dec = this.inRC4.cypher(buf);

                //Logger.log("IN", type.name() + " " + id, LogLevel.DEBUG);

                if (this.listeners.has(type)) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(dec);
                    DataInputStream in = new DataInputStream(bis);

                    try {
                        Packet p = type.newInstance();
                        p.read(in);
                        try {
                            this.listeners.invoke(p);
                            this.partner.send(p);
                        } catch (Exception e) {
                            Logger.log("IO", "Error while calling " + type.name() + " listeners!");
                            e.printStackTrace();
                        }
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    } finally {
                        bis.close();
                        in.close();
                    }
                } else {
                    this.partner.send(type, dec);
                }
            } catch (IOException e) {
                //Logger.log("DEBUG", "isclient " + (this.RC4_INCOMING_KEY == Environment.RC4_CLIENT_KEY));
                //e.printStackTrace();
                this.disconnect();
            }
        }
    }

    public void send(Packet p) throws IOException {
        if (!this.connected || this.isBlocked(PacketType.typeOf(p))) return;
        //Logger.log("OUTMOD", PacketType.typeOf(p).name(), LogLevel.DEBUG);

        byte id = PacketMapper.get(PacketType.typeOf(p));
        byte[] buf = this.packetToByteArray(p);
        buf = this.outRC4.cypher(buf);

        this.output.writeInt(buf.length + 5);
        this.output.writeByte(id);
        this.output.write(buf);
    }

    public void send(PacketType type, byte[] dec) throws IOException {
        if (!this.connected || this.isBlocked(type)) return;
        //Logger.log("OUT", type.name(), LogLevel.DEBUG);

        byte[] buf = this.outRC4.cypher(dec);

        this.output.writeInt(buf.length + 5);
        this.output.writeByte(PacketMapper.get(type));
        this.output.write(buf);
    }

    public void blockNext(PacketType pt) {
        if (!this.blocked.contains(pt)) this.blocked.add(pt);
    }

    public boolean isBlocked(PacketType pt) {
        return this.blocked.remove(pt);
    }

    public void setPartner(ProxyIO io) {
        this.partner = io;
    }

    private byte[] packetToByteArray(Packet p) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        try {
            p.write(out);
        } finally {
            try {
                baos.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    public void disconnect() {
        if (!this.connected) return;
        this.connected = false;
        Logger.log("ProxyIO", "Disconnected from: " + this.socket.getInetAddress().getHostName());
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.listeners.invoke(ListenerType.DISCONNECT);
        } catch (Exception e) {
            Logger.log("ProxyIO", "Error while calling disconnect listeners!");
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.connected;
    }
}
