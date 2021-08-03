package ovh.angrysoft.jiot.connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import ovh.angrysoft.jiot.exceptions.connctions.DeviceConnectionError;


public class UdpConnection {
    protected DatagramSocket socket;
    private int retryNumber = 3;

    public UdpConnection() throws DeviceConnectionError {
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(5);
        } catch (SocketException e ) {
            throw new DeviceConnectionError("Create udp socket error: " + e);
        }

    }

    public void sendJson(Map<String,?> msg, String addr, int port, int retry) {
        
    }

    public void send(String msg, String addr, int port) throws DeviceConnectionError {
        int retry = this.getRetryNumber();
        while (retry != 0) {
            byte[] buf = msg.getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(addr), port);
                this.socket.send(packet);
                return;
            } catch ( IOException e ) {
                if (retry == 1)
                    throw new DeviceConnectionError("packet send error: " + e );
            }
            retry--;
        }
    }

    public String recv() throws DeviceConnectionError {
        int retry = this.getRetryNumber();
        var ret = "";
        while (retry != 0) {
            try {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                this.socket.receive(packet);
                ret = this.getStringFromArrayByte(packet.getData());
                break;
            } catch (IOException e) {
                if (retry == 1)
                    throw new DeviceConnectionError("packet send error: " + e );
            }
            retry--;
        }
        return ret;
    }

    private String getStringFromArrayByte(byte[] arrayIN) {
        int offset = -1;
        for (int i = 0; i < arrayIN.length; i++) {
            if (arrayIN[i] == 0) {
                offset = i;
                break;
            }
        }
        
        return new String(arrayIN, 0 , offset, StandardCharsets.UTF_8);
    }

    public int getRetryNumber() {
        return this.retryNumber;
    }
    public void close() {
        this.socket.close();
    }
}
