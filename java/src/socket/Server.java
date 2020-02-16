package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        Handler requestHandle = new Handler();
        try(ServerSocket serverSocket = new ServerSocket(6666)){
            System.out.println("Listening on " + serverSocket.getLocalSocketAddress());
            while (true){
                Socket socketClient = serverSocket.accept();
                System.out.println("Accepting incoming from"
                        + socketClient.getRemoteSocketAddress());
                new ClientHandle(socketClient, requestHandle).run();
            }

        }
    }
}

