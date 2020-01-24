package org.bloomdex.server;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;

public class Server {
    private static int port = 0;
    private static String IP;
    public static boolean PRINT_INFO = false;
    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};

    /**
     * Creates a server thread for every client
     * @throws IOException throws an exception if:
     *      something went wrong with creating the server socket
     *      something went wrong with getting the public IP of the server
     */
    private static void createServerThread() throws IOException {
        SSLServerSocket socket = createServerSocket();

        System.out.println("The server started on public IP-address " + getPublicIP());
        System.out.println("The server started on port " + socket.getLocalPort());

        try {
            while (true) {
                new ServerThread(socket.accept()).start();
            }
        } catch (Exception e) {
            System.out.printf("exception: %s%n", e.getMessage());
        }
        System.out.println("Server stopped.");
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
     * Gets the public IP of the server from http://checkip.amazonaws.com
     *
     * @return returns the public IP of the server
     * @throws IOException throws an exception if something went wrong with the request for the public ip of the server on http//checkip.amazonaws.com
     */
    public static String getPublicIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        return in.readLine();
    }

    /**
     * Returns the open port of the server
     *
     * @return Returns the open port of the server
     */
    public static int getPort() {
        return port;
    }

    /**
     * Returns the IP of the server
     *
     * @return returns the IP of the server
     */
    public static String getIP() {
        return IP;
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

        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        createServerThread();
    }
}
