package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import test.Commands.DefaultIO;

public class AnomalyDetectionHandler implements Server.ClientHandler{

    public class SocketIO implements DefaultIO{

        Scanner in;
        PrintWriter out;
        public SocketIO(InputStream inFromClient, OutputStream outToClient) {
            in=new Scanner(inFromClient);
            out=new PrintWriter(outToClient);
        }

        @Override
        public String readText() {
            return in.nextLine();
        }

        @Override
        public void write(String text) {
            out.print(text);
            out.flush();
        }

        @Override
        public float readVal() {
            return in.nextFloat();
        }

        @Override
        public void write(float val) {
            out.print(val);
            out.flush();
        }

        public void close() {
            in.close();
            out.close();
        }
    }

    @Override
    public void handle(InputStream inFromClient, OutputStream outToClient) {
        SocketIO sio=new SocketIO(inFromClient, outToClient);
        CLI cli=new CLI(sio);
        cli.start();
        sio.close();
    }

}
