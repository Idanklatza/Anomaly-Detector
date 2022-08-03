package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    public interface ClientHandler{
        // define
        void handle(InputStream inFromClient, OutputStream outToClient);
    }

    volatile boolean stop;
    public Server() {
        stop=false;
    }


    private void startServer(int port, ClientHandler ch) {
        try {
            ServerSocket server=new ServerSocket(port);
            server.setSoTimeout(1000);
            while(!stop) {
                try{
                    Socket aClient=server.accept();
                    ch.handle(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.close();
                }catch(SocketTimeoutException e) {}
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int port, ClientHandler ch) {
        new Thread(()->startServer(port,ch)).start();
    }

    public void stop() {
        stop=true;
    }
}
