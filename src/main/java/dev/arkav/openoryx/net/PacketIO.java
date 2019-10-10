package dev.arkav.openoryx.net;

import dev.arkav.openoryx.Environment;
import dev.arkav.openoryx.util.logging.Logger;
import dev.arkav.openoryx.game.models.Server;
import dev.arkav.openoryx.net.crypto.HexUtil;
import dev.arkav.openoryx.net.crypto.RC4;
import dev.arkav.openoryx.net.data.Packet;
import dev.arkav.openoryx.net.listeners.ListenerStore;
import dev.arkav.openoryx.net.listeners.ListenerType;
import dev.arkav.openoryx.net.packets.PacketType;
import dev.arkav.openoryx.net.packets.c2s.HelloPacket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class PacketIO implements Runnable {
    // Socket
    private Socket socket;

    private DataInputStream input;
    private DataOutputStream output;

    private boolean connected;
    // Crypto
    private RC4 inRC4;
    private RC4 outRC4;
    // Listener cache
    private ListenerStore listeners;

    /**
     * PacketIO
     * @param server Server to connect to.
     * @param hello Hello Packet to send to the server.
     * @param hooks Packet listeners.
     * @throws IOException
     */
    public PacketIO(Server server, HelloPacket hello, ListenerStore hooks) throws IOException {
        this.connect(new Socket(server.getHost(), server.getPort()), hello, hooks);
    }

    /**
     * PacketIO
     * @param server Server to connect to.
     * @param proxy Needs to be a socks proxy {new java.net.Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port))}
     * @param hello Hello Packet to send to the server.
     * @param listeners Packet listeners.
     * @throws IOException
     */
    public PacketIO(Server server, Proxy proxy, HelloPacket hello, ListenerStore listeners) throws IOException {
        Socket s = new Socket(proxy);

        s.connect(new InetSocketAddress(server.getHost(), server.getPort()));
        this.connect(s, hello, listeners);
    }

    private void connect(Socket socket, HelloPacket hello, ListenerStore listeners) throws IOException {
        this.socket = socket;
        this.socket.setTcpNoDelay(true);
        this.input = new DataInputStream(this.socket.getInputStream());
        this.output = new DataOutputStream(this.socket.getOutputStream());

        this.inRC4 = new RC4(HexUtil.hexStringToByteArray(Environment.RC4_CLIENT_KEY));
        this.outRC4 = new RC4(HexUtil.hexStringToByteArray(Environment.RC4_SERVER_KEY));
        this.listeners = listeners;

        this.connected = true;

        new Thread(this).start();

        while (true) {
            if (this.socket.isConnected()) {
                this.send(hello);
                break;
            }
        }

        try {
            this.listeners.invoke(ListenerType.CONNECT);
        } catch (Exception e) {
            Logger.log("IO", "Error while calling CONNECT listeners!");
        }
    }

    @Override
    public void run() {
        while (this.connected) {
            try {
                if (this.socket.isClosed()) throw new IOException("Remote host forcibly closed the connection!");

                int bufSize = this.input.readInt() - 5;
                byte id = this.input.readByte();
                PacketType type = PacketMapper.get(id);
                byte[] buf = new byte[bufSize];
                this.input.readFully(buf);
                buf = this.inRC4.cypher(buf);

                if (this.listeners.has(type)) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                    DataInputStream in = new DataInputStream(bis);

                    try {
                        Packet p = type.newInstance();
                        p.read(in);
                        try {
                            this.listeners.invoke(p);
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
                }
            } catch (IOException e) {
                this.disconnect(true);
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a packet to the server.
     * @param packet The packet to send.
     */
    public void send(Packet packet) throws IOException {
        if (!this.connected) return;

        byte id = PacketMapper.get(PacketType.typeOf(packet));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        try {
            packet.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                baos.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] buf = this.outRC4.cypher(baos.toByteArray());
        this.output.writeInt(buf.length + 5);
        this.output.writeByte(id);
        this.output.write(buf);
        this.output.flush();
    }

    /**
     * Disconnects the client from the server, this Packet
     * @param callListeners weather or not to call disconnect listeners.
     */
    public void disconnect(boolean callListeners) {
        if (!this.connected) return;
        this.connected = false;
        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (callListeners) {
            try {
                this.listeners.invoke(ListenerType.DISCONNECT);
            } catch (Exception e) {
                Logger.log("IO", "Error while calling DISCONNECT listeners");
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return this.connected;
    }
}
