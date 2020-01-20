package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.bloomdex.datamcbaseface.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.bloomdex.datamcbaseface.controller.AbstractController.api_prefix;

@RestController
@RequestMapping(value = api_prefix + "measurement")
public class MeasurementController extends AbstractController
{
    @Autowired
    MeasurementRepository repo;

    /**
     * Usage: "GET /api/v1/measurement/{measurement_id}/"
     *
     * @param measurement_id The id of the measurement information should be retrieved from.
     * @return Information about the given measurement
     * @throws InvalidRequestException Will be thrown when client parameters are invalid.
     * @throws NoEntriesFoundException Will be thrown when no valid measurement has been returned.
     */
    @RequestMapping("/{measurement_id}")
    public Measurement getMeasurementById(@PathVariable long measurement_id)
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
