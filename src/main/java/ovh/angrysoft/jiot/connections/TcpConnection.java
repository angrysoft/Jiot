package ovh.angrysoft.jiot.connections;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import ovh.angrysoft.jiot.exceptions.connctions.DeviceConnectionError;

public class TcpConnection {
    private InetAddress addr;
    private int port;
    private Socket socket;
    public TcpConnection(String ip, int port) throws DeviceConnectionError {
        this.port = port;
        try {
            this.addr = InetAddress.getByName(ip);
            this.socket = new Socket(addr, port);
        } catch (IOException e) {
            throw new DeviceConnectionError("Create Tcp socket error: " + e);
            
        }
    }
    
}
