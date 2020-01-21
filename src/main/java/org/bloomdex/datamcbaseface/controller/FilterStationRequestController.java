package org.bloomdex.datamcbaseface.controller;

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

@RestController
public class FilterStationRequestController extends AbstractController {
    /**
     * @param connectionType The type of connection that should be made with a server.
     * @return A Map containing a message if the requested connection type succeeded or failed.
     * @throws InvalidRequestException When an invalid connection-type is given.
     */
    @RolesAllowed("ROBOT")
    @RequestMapping(api_prefix + "connection")
    public Map<String, Object> requestConnectionType(@RequestParam(value = "type", defaultValue = "") String connectionType)
            throws InvalidRequestException
    {
        var returnMessage = new HashMap<String, Object>();

        if(connectionType.equals("request_connection"))
            openConnection(returnMessage);
        else
            throw new InvalidRequestException();

        return returnMessage;
    }

    /**
     * Temporary placeholder for opening a connection with a FilterStation.
     * @param returnMessage A map containing key+values that should be returned when the given API is requested.
     */
    private void openConnection(Map<String, Object> returnMessage){
        try {
            returnMessage.put("response", "LISTENING");
            returnMessage.put("ip_address", getPublicIp());
            returnMessage.put("port", 50000);
        } catch (Exception e) {
            returnMessage.put("response", "FAILED");
        }
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
