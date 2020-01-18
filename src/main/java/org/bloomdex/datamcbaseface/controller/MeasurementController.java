package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.bloomdex.datamcbaseface.repository.MeasurementRepository;
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
     * @param measurement_id The id of the measurement information should be retrieved from.
     * @return Information about the given measurement
     * @throws InvalidRequestException Will be thrown when client parameters are invalid.
     * @throws NoEntriesFoundException Will be thrown when no valid measurement has been returned.
     */
    @RequestMapping(api_prefix + "measurement")
    public Measurement getMeasurementById(@RequestParam(value = "id", defaultValue = "-1") long measurement_id)
            throws InvalidRequestException, NoEntriesFoundException
    {
        if(measurement_id < 0)
            throw new InvalidRequestException();

        var measurement = repo.findById(measurement_id);

        if(measurement.isEmpty())
            throw new NoEntriesFoundException();

        return measurement.get();
    }
}
