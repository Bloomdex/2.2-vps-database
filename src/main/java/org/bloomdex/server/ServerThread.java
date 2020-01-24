package org.bloomdex.server;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private final Socket socket;
    private DataInputStream dataInputStream;
    private byte[] data;

    /**
     * The constructor which sets the socket as a variable
     *
     * @param socket the socket which receives the data
     */
    ServerThread(Socket socket){
        this.socket = socket;
        System.out.println("new client accepted");
    }

    /**
     * Runs the main loop from the thread which is receiving the data
     */
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            while (!socket.isClosed()){
                int length = dataInputStream.readInt();
                if(length>0) {
                    data = new byte[length];
                    dataInputStream.readFully(data, 0, data.length);

                    // need to be done!!!
                    // notify the database code there is new data that can be inserted into the database
                }

                // prints the incoming data
                /*
                for (int x = 0; x <= length - 1; x++) {
                    System.out.print((char)data[x]);
                }
                System.out.print("\n");
                 */
            }
        } catch (IOException e) {
            System.out.printf("exception: %s%n", e.getMessage());
        }
    }

    /**
     * Returns the incoming data
     *
     * @return returns the incoming data
     */
    public byte[] getData() {
        return data;
    }
}

