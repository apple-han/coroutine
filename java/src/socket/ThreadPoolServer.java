package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Handler requestHandle = new Handler();
        try(ServerSocket serverSocket = new ServerSocket(8888)){
            System.out.println("Listening on " + serverSocket.getLocalSocketAddress());
            while (true){
                Socket socketClient = serverSocket.accept();
                System.out.println("Accepting incoming from"
                        + socketClient.getRemoteSocketAddress());
                executor.submit(new ClientHandle(socketClient, requestHandle));
            }

        }
    }
}
