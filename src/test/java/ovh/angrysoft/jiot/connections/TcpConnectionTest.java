package ovh.angrysoft.jiot.connections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import ovh.angrysoft.jiot.exceptions.connctions.DeviceConnectionError;

public class TcpConnectionTest {
    TcpConnection client;

    @BeforeEach
    void setup() throws DeviceConnectionError {
        new EchoTcpServer(6666).start();
        this.client = new TcpConnection("localhost", 6666);

    }

    @Test
    @DisplayName("TCP send")
    void sendAndGetEcho() throws DeviceConnectionError {
        this.client.send("hello tcp server");
        String echo = this.client.recv();
        assertEquals("hello tcp client", echo);
    }
    
    @AfterEach
    void close() {
        this.client.send("end");
        this.client.close();
    }
}

class EchoTcpServer extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int port;

    public EchoTcpServer(int port) {
        this.port = port;
    }
    
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            boolean running = true;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("before loop");
            while(running) {
                this.clientSocket = this.serverSocket.accept();
                String greeting = in.readLine();
                if ("hello tcp server".equals(greeting)) {
                    out.println("hello tcp client");
                } else if (greeting.equals("end")) {
                    running = false;
                    break;
                }
                else {
                    out.println("unrecognized greeting");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        this.closeAll();
    }

    public void closeAll() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}