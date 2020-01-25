package org.bloomdex.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private final Socket socket;
    private DataInputStream dataInputStream;
    private byte[] data;

    private static final Logger Logger = LoggerFactory.getLogger(ServerThread.class);

    /**
     * The constructor which sets the socket as a variable
     *
     * @param socket the socket which receives the data
     */
    ServerThread(Socket socket){
        this.socket = socket;
        Logger.info("New data-sending client accepted.");
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

            Logger.info("Closed connection with data-sending client.");
        } catch (IOException e) {
            Logger.error("Exception: " + e.getMessage());
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

