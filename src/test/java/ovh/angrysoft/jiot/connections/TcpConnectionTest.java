package ovh.angrysoft.jiot.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpConnectionTest {
    
}

class GreetServer extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
                if ("hello server".equals(greeting)) {
                    out.println("hello client");
                }
                else {
                    out.println("unrecognized greeting");
                }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /* public void stop() {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    } */
}