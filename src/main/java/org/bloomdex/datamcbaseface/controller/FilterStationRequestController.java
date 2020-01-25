package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.server.Server;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RolesAllowed("ROBOT")
@RestController
public class FilterStationRequestController extends AbstractController {
    /**
     * @param connectionType The type of connection that should be made with a server.
     * @return A Map containing a message if the requested connection type succeeded or failed.
     * @throws InvalidRequestException When an invalid connection-type is given.
     */
    @RequestMapping(api_prefix + "connection")
    public Map<String, Object> requestConnectionType(
            @RequestParam(value = "type", defaultValue = "") String connectionType)
            throws InvalidRequestException
    {
        Map<String, Object> returnMessage;

        if(connectionType.equals("request_connection"))
            returnMessage = openConnection();
        else if(connectionType.equals("stop_server"))
            returnMessage = closeConnection();
        else
            throw new InvalidRequestException();

        return returnMessage;
    }

    /**
     * Method that starts a server so a FilterStation can connect to it.
     * @return A Map with information for FilterStation, to check if a connection is possible.
     */
    private Map<String, Object> openConnection(){
        HashMap<String, Object> returnMessage = new HashMap<>();

        try {
            if(!Server.ServerRunning) {
                System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
                System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");

                Server.createServerThread();
            } else {
                returnMessage.put("message", "Server already running.");
            }

            returnMessage.put("response", "LISTENING");
            returnMessage.put("ip_address", getPublicIp());
            returnMessage.put("port", Server.port);

        } catch (Exception e) {
            returnMessage.put("response", "FAILED");
            e.printStackTrace();
            returnMessage.put("exception", e.toString());
        }

        return returnMessage;
    }

    /**
     * Method that stops the running data-collection server.
     * @return A Map with information for the FilterStation, to check if the server has been stopped.
     */
    private Map<String, Object> closeConnection() {
        HashMap<String, Object> returnMessage = new HashMap<>();

        if(Server.ServerRunning) {
            Server.ServerRunning = false;
            returnMessage.put("response", "SUCCESS");
        } else {
            returnMessage.put("response", "FAILED");
            returnMessage.put("message", "Server not running");
        }

        return returnMessage;
    }

    /**
     * source: https://stackoverflow.com/a/14541376
     * @return Public IP-address as a String from amazonaws.com
     * @throws Exception When retrieving the ip-address fails.
     */
    private static String getPublicIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
