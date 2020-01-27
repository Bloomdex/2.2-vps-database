package org.bloomdex.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;

public class Server implements Runnable {
    public static boolean PRINT_INFO = false;
    private static final Logger Logger = LoggerFactory.getLogger(Server.class);

    private volatile boolean isServerRunning;
    private SSLServerSocket serverSocket;

    private final int port;
    private final String keyStorePath;
    private final String keyStorePass;

    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};

    /**
     * Constructor for Server where a custom port, KeyStore path and KeyStore password can be set
     * @param port The port the server should be running on.
     * @param keyStorePath The path of the keystore.
     * @param keyStorePass The password of the keystore.
     */
    public Server(int port, String keyStorePath, String keyStorePass) {
        this.port = port;
        this.keyStorePath = keyStorePath;
        this.keyStorePass = keyStorePass;
    }

    /**
     * Creates a server thread for every client
     * @throws IOException throws an exception if:
     *      something went wrong with creating the server socket
     *      something went wrong with getting the public IP of the server
     */
    public void createServerThread() throws IOException {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePass);
        serverSocket = createServerSocket();

        try {
            Logger.info("The data-collection server started on port " + serverSocket.getLocalPort());
            isServerRunning = true;

            while (isServerRunning) {
                new ServerThread(serverSocket.accept()).start();
            }
        } catch (Exception e) {
            Logger.error("Exception: " + e.getMessage());
        } finally {
            serverSocket.close();
        }

        isServerRunning = false;
        Logger.info("Stopped data-collection-server on port: " + port);
    }

    /**
     * @return If this instance of a Server is running or not.
     */
    public boolean isServerRunning() {
        return isServerRunning;
    }

    /**
     * @return the Port the server (will be) running on.
     */
    public int getPort() {
        return port;
    }

    /**
     * Creates a SSL server socket to allow encrypted connections
     *
     * @return a SSL server socket
     * @throws IOException throws an exception if something went wrong with creating the SSL server socket
     */
    private SSLServerSocket createServerSocket() throws IOException {
        SSLServerSocket socket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
        socket.setEnabledProtocols(protocols);
        socket.setEnabledCipherSuites(cipher_suites);

        return socket;
    }

    /**
     * The implemented run method required to create a server thread.
     */
    @Override
    public void run() {
        try {
            createServerThread();
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } finally {
            isServerRunning = false;
            Logger.info("Stopped Thread of data-collection-server.");
        }
    }

    /**
     * Method to stop the server from running.
     */
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } finally {
            isServerRunning = false;
        }
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

        Server server = new Server(
                25565,
                "/var/datamcbaseface/keystore.jks",
                "passphrase"
        );
        server.createServerThread();
    }
}
