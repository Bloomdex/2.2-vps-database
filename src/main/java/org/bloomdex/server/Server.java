package org.bloomdex.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;

public class Server {
    public static boolean PRINT_INFO = false;
    private static final Logger Logger = LoggerFactory.getLogger(Server.class);

    public static boolean ServerRunning = false;
    public static int port = 25565;

    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};

    /**
     * Creates a server thread for every client
     * @throws IOException throws an exception if:
     *      something went wrong with creating the server socket
     *      something went wrong with getting the public IP of the server
     */
    public static void createServerThread() throws IOException {
        SSLServerSocket socket = createServerSocket();
        Logger.info("The data-collection server started on port " + socket.getLocalPort());

        try {
            ServerRunning = true;

            while (ServerRunning) {
                new ServerThread(socket.accept()).start();
            }

            socket.close();
        } catch (Exception e) {
            Logger.error("Exception: " + e.getMessage());
        }

        ServerRunning = false;
        Logger.info("Data-collection server on port: " + port + "stopped.");
    }

    /**
     * Creates a SSL server socket to allow encrypted connections
     *
     * @return a SSL server socket
     * @throws IOException throws an exception if something went wrong with creating the SSL server socket
     */
    private static SSLServerSocket createServerSocket() throws IOException {
        SSLServerSocket socket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
        socket.setEnabledProtocols(protocols);
        socket.setEnabledCipherSuites(cipher_suites);

        return socket;
    }

    /**
     *  The main function witch starts the server
     *
     * @param args if verbose is given as an arg the server will print his port and IP address
     * @throws IOException throws an exception when something went wrong with creating a server thread
     */
    public static void main(String[] args) throws IOException {
        if(args.length > 0) {
            if(args[0].equals("--verbose") || args[0].equals("-v"))
                PRINT_INFO = true;
        }

        System.setProperty("javax.net.ssl.keyStore", "/var/datamcbaseface/keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        createServerThread();
    }
}
