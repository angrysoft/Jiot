package ovh.angrysoft.jiot.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import ovh.angrysoft.jiot.exceptions.connctions.DeviceConnectionError;

public class TcpConnection {
    private InetAddress addr;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public TcpConnection(String ip, int port) throws DeviceConnectionError {
        this.port = port;
        try {
            this.addr = InetAddress.getByName(ip);
            this.socket = new Socket(addr, port);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new DeviceConnectionError("Tcp socket error: " + e);
            
        }
    }

    public void send(String msg) {
        this.out.println(msg);
    }

    public String recv() throws DeviceConnectionError {
        try {
            return this.in.readLine();
        } catch (IOException e) {
            throw new DeviceConnectionError("Receive error: " + e);
        }
    }

    public void close() throws DeviceConnectionError {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            throw new DeviceConnectionError("Receive error: " + e);
        }
    }
    
}
