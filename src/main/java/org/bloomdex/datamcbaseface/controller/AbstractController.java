package org.bloomdex.datamcbaseface.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {
    protected static final String api_prefix = "api/v1/";

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

    /**
     * Exception that will be thrown when a no entries were found from the given request.
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "No Entries Found")
    public class NoEntriesFoundException extends Exception { }

    //TODO: actually check a database for a valid authentication string
    /**
     * @param authentication the authentication string, should be a string of 48 random characters a-z, A-Z, 0-9
     * @return Returns true if the authentication matches an authentication string that's in a database
     */
    protected boolean CheckAuthentication(String authentication)
    {
        if (authentication.length() != 48)
            return false;

        return authentication.equals("JijNqZJxbUXMfprRxFnkfPnyJodgcnknaJ7iW6j2r5diHSKb");
    }
}
