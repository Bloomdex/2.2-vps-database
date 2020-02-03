package org.bloomdex.server;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.bloomdex.datamcbaseface.repository.MeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.*;
import java.util.List;

public class ServerThread extends Thread {
    private final Socket socket;
    private DataInputStream dataInputStream;
    private byte[] data;

    private static final Logger Logger = LoggerFactory.getLogger(ServerThread.class);

    private MeasurementRepository repo;

    /**
     * The constructor which sets the socket as a variable
     *
     * @param socket the socket which receives the data
     */
    ServerThread(Socket socket, MeasurementRepository repo){
        this.socket = socket;
        this.repo = repo;
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

                    List<Measurement> measurements = Converter.InputStreamToMeasurements(data);

                    if(measurements != null) {
                        repo.saveAll(measurements);
                        Logger.info("Add " + measurements.size() + " new measurements to the database.");
                    }
                }
            }

        }
        catch (SSLException ignore) {}
        catch (IOException e) {
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

