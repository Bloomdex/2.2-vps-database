package org.bloomdex.datamcbaseface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController extends AbstractController
{
    @Autowired
    MeasurementRepository repo;

    /**
     * @param authentication The authentication string, should be a string of 48 random characters a-z, A-Z, 0-9
     * @param measurement_id The id of the measurement information should be retrieved from.
     * @return Information about the given measurement
     * @throws InvalidRequestException Will be thrown when client parameters are invalid.
     * @throws InvalidAuthenticationException Will be thrown when insufficient authentication is given.
     * @throws NoEntriesFoundException Will be thrown when no valid measurement has been returned.
     */
    @RequestMapping(api_prefix + "measurement")
    public Measurement getMeasurementById(
            @RequestParam(value = "auth", defaultValue = "") String authentication,
            @RequestParam(value = "id", defaultValue = "-1") long measurement_id)
            throws InvalidRequestException, InvalidAuthenticationException, NoEntriesFoundException
    {
        if(!CheckAuthentication(authentication))
            throw new InvalidAuthenticationException();

        if(measurement_id < 0)
            throw new InvalidRequestException();

        var measurement = repo.findById(measurement_id);

        if(measurement.isEmpty())
            throw new NoEntriesFoundException();

        return measurement.get();
    }
}
