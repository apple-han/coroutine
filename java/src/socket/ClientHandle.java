package socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandle implements Runnable{
    private final Socket socketClient;
    private final Handler requestHandle;

    public ClientHandle(
            Socket socketClient, Handler requestHandle){
        this.socketClient = socketClient;
        this.requestHandle = requestHandle;
    }
    @Override
    public void run() {
        try(Scanner input = new Scanner(socketClient.getInputStream())) {
            while (true){
                String request = input.nextLine();
                if (request.equals("quit")){
                    break;
                }
                String response = requestHandle.handle(request);
                socketClient.getOutputStream().write(
                        response.getBytes());
            }
        }catch (IOException e){
            System.out.println("exception " + e);
            throw new RuntimeException(e);
        }
    }
}
