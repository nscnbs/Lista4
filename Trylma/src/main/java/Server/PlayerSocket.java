package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * Socket for player
 */

public class PlayerSocket {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public PlayerSocket(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public synchronized Socket getSocket() {
        return socket;
    }

    public synchronized void close() {
        out.close();

        try {
            in.close();
            socket.close();
        } catch (IOException e) {
        }
    }

    public synchronized void sendStr(String str) {
        out.println(str);
        out.flush();
    }
    public synchronized void sendInt(int index){
        out.println(index);
        out.flush();
    }

    public synchronized String receive() {
        String temp = null;
        try {
            if(in.ready()) {
                temp = in.readLine();
            }
        } catch (IOException e) {}

        return temp;
    }


}
