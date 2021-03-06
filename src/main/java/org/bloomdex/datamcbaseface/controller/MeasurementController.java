package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.bloomdex.datamcbaseface.model.TimeFrame;
import org.bloomdex.datamcbaseface.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MeasurementController extends AbstractController
{
    @Autowired
    MeasurementRepository repo;

    private SimpleDateFormat simpleDateFormat;

    public MeasurementController() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

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

        Optional<Measurement> measurement = repo.findById(measurement_id);

        if(!measurement.isPresent())
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

    /**
     * USAGE: "GET /api/v1/measurements/average"
     * BODY:
     *   {
     *      "startDate": "2020-01-26 12:00:00",
     *      "endDate": "2020-01-26 14:00:00"
     *   }
     *
     * @param timeFrame The time-frame where the average should be taken from.
     * @param pageable The pageable settings for requested averages.
     * @return A paged list of averages from all weather-stations.
     * @throws ParseException
     */
    @RequestMapping(api_prefix + "measurements/average")
    public List<Map<String, Object>> getAverageMeasurementByTimeFrameGroupByStationId(
            @RequestBody TimeFrame timeFrame,
            Pageable pageable)
            throws ParseException
    {
        Date firstDate = simpleDateFormat.parse(timeFrame.getStartDate());
        Date secondDate = simpleDateFormat.parse(timeFrame.getEndDate());

        List<Map<String, Object>> value = repo.getAverageBetweenDatesGroupByStationId(pageable, firstDate, secondDate);
        for (Map<String, Object> map : value) {
            map.put("date", simpleDateFormat.format(firstDate));
        }

        return value;
    }

    /**
     * USAGE:  "GET /api/v1/stations/{station_id}/measurements/average"
     * BODY:
     *   {
     *      "startDate": "2020-01-26 12:00:00",
     *      "endDate": "2020-01-26 14:00:00"
     *   }
     *
     * @param station_id The station an average should be returned from.
     * @param timeFrame The time-frame where the average should be taken from.
     * @return A list of averages from values in Measurement
     * @throws InvalidRequestException
     * @throws ParseException
     */
    @RequestMapping(api_prefix + "stations/{station_id}/measurements/average")
    public Map<String, Object> getAverageMeasurementByTimeFrameAndStationId(
            @PathVariable int station_id,
            @RequestBody TimeFrame timeFrame)
            throws InvalidRequestException, ParseException
    {
        if(station_id < 0)
            throw new InvalidRequestException();

        Date firstDate = simpleDateFormat.parse(timeFrame.getStartDate());
        Date secondDate = simpleDateFormat.parse(timeFrame.getEndDate());

        Map<String, Object> value = repo.getAverageBetweenDatesByStationId( firstDate, secondDate, station_id);

        return value;
    }

    /**
     * @param station_id The station information should be retrieved from.
     * @return A list of averages per day for the last month from the given station.
     * @throws InvalidRequestException When an invalid stationId has been given.
     */
    @RequestMapping(value = api_prefix + "stations/{station_id}/measurements/average/month",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Map<String, Object>> getAverageMeasurementsGroupByDayFromLastMonthByStationId(
            @PathVariable int station_id,
            @RequestParam(value = "type", required = false) String type)
            throws InvalidRequestException
    {
        if(station_id < 0)
            throw new InvalidRequestException();

        if(type == null)
            return repo.getAverageLastMonthGroupByDayByStationId(station_id);
        if(type.equalsIgnoreCase("vegaflor"))
            return repo.getVegaflorSpecificationsLastMonthByStationId(station_id);
        else
            throw new InvalidRequestException();
    }

    /**
     * @param limit The amount of results that should be returned from the query.
     * @return The most desirable circumstances for flowers to grow in south-america according to Vegaflor's specification.
     */
    @RequestMapping(api_prefix + "measurements/average/month/desirable")
    public List<Map<String, Object>> getVegaflorMostDesirableByHumidityAndTemperature(
            @RequestParam(value = "limit", required = false) Integer limit)
    {
        int maxResults = 10;
        if(limit != null)
            maxResults = limit;

        return repo.getVegaflorMostDesirableByHumidityAndTemperature(PageRequest.of(0, maxResults));
    }
}
