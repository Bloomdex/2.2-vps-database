package org.bloomdex.datamcbaseface;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController
{
    @RequestMapping("/measurement.json")
    public Measurement getTestMeasurement(
            @RequestParam(value = "station_id", defaultValue = "-1") long station_id,
            @RequestParam(value = "auth", defaultValue = "") String authentication)
            throws InvalidRequestException, InvalidAuthenticationException
    {
        if(!CheckAuthentication(authentication))
            throw new InvalidAuthenticationException();

        if(station_id < 0)
            throw new InvalidRequestException();

        Measurement measurement = new Measurement();

        return measurement;
    }

    /**
     * Exception that will be thrown when the given authentication string does not match one in the database
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Authentication")
    public class InvalidAuthenticationException extends Exception { }

    /**
     * Exception that will be thrown when a request is invalid (Possibly by giving the wrong parameters in the request)
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Request")
    public class InvalidRequestException extends Exception { }

    //TODO: actually check a database for a valid authentication string
    /**
     * @param authentication the authentication string, should be a string of 48 random characters a-z, A-Z, 0-9
     * @return Returns true if the authentication matches an authentication string that's in a database
     */
    private boolean CheckAuthentication(String authentication)
    {
        if (authentication.length() != 48)
            return false;

        return authentication.equals("JijNqZJxbUXMfprRxFnkfPnyJodgcnknaJ7iW6j2r5diHSKb");
    }
}
