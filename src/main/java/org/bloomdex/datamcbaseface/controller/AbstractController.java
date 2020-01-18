package org.bloomdex.datamcbaseface.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {
    public static final String api_prefix = "api/v1/";

    /**
     * Exception that will be thrown when a request is invalid (Possibly by giving the wrong parameters in the request)
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Request")
    public class InvalidRequestException extends Exception { }

    /**
     * Exception that will be thrown when a no entries were found from the given request.
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Entries Found")
    public class NoEntriesFoundException extends Exception { }

}
