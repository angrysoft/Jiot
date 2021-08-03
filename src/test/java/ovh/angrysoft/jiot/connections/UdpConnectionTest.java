package ovh.angrysoft.jiot.connections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ovh.angrysoft.jiot.exceptions.connctions.DeviceConnectionError;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UdpConnectionTest {
    UdpConnection client;

    @BeforeEach
    public void setup() throws DeviceConnectionError, SocketException {
        new EchoUdpTestServer().start();
        this.client = new UdpConnection();
    }

    @Test
    @DisplayName("Udp Send")
    void sendAndGetEcho() throws DeviceConnectionError {
        this.client.send("hello server", "localhost", 6666);
        String echo = this.client.recv();
        System.out.println(echo);
        assertEquals("hello server", echo);
    }

   /*  @Test
    void sendJsonAndGetEcho() {} */

    @AfterEach
    void tearDown() throws DeviceConnectionError {
        this.client.send("end", "localhost", 6666);
        this.client.close();
    }
}


class EchoUdpTestServer  extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];

    public EchoUdpTestServer() throws SocketException {
        socket = new DatagramSocket(6666);
    }

    public void run() {
        running = true;

        while (running) {
            try { 
                DatagramPacket packet 
                = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received 
                = new String(packet.getData(), 0, packet.getLength());
                
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch ( IOException e) {
                System.err.println(e);
                running = false;
                continue;
            }
        }
        socket.close();
    }
}