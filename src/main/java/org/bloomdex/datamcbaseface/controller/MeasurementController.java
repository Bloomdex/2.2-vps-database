package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.bloomdex.datamcbaseface.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController extends AbstractController
{
    @Autowired
    MeasurementRepository repo;

    /**
     * Usage: "GET /api/v1/measurements/{measurement_id}/"
     *
     * @param measurement_id The id of the measurement information should be retrieved from.
     * @return Information about the given measurement
     * @throws InvalidRequestException Will be thrown when client parameters are invalid.
     * @throws NoEntriesFoundException Will be thrown when no valid measurement has been returned.
     */
    @RequestMapping(api_prefix + "measurements/{measurement_id}/")
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

    /**
     * @param pageable The pageable settings for requesting measurements
     * @param station_id The id of the station you want to retrieve measurements from.
     * @return A paged list of all measurements requested.
     * @throws InvalidRequestException Will be thrown when client parameters are invalid.
     */
    @RequestMapping(api_prefix + "paged/measurements")
    public Page<Measurement> getAllMeasurements(
            Pageable pageable,
            @RequestParam(value = "station_id", required = false) Integer station_id)
            throws InvalidRequestException
    {
        if(station_id != null) {
            if(station_id < 0) throw new InvalidRequestException();

            return repo.findMeasurementsByStationId(pageable, station_id);
        }

        return repo.findAll(pageable);
    }
}
