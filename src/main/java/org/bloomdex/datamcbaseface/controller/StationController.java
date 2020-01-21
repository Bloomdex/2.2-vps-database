package org.bloomdex.datamcbaseface.controller;

import org.bloomdex.datamcbaseface.model.Station;
import org.bloomdex.datamcbaseface.repository.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StationController extends AbstractController {

    @Autowired
    StationRepository repo;

    /**
     * @param station_id The id of the station you want to retrieve information from.
     * @return Info about the requested station.
     * @throws InvalidRequestException Will be thrown when client parameters are invalid/
     * @throws NoEntriesFoundException Will be thrown when no valid station has been returned.
     */
    @RequestMapping(api_prefix + "station/{station_id}/")
    public Station getStationById(
            @PathVariable long station_id)
            throws InvalidRequestException, NoEntriesFoundException
    {
        if(station_id < 0)
            throw new InvalidRequestException();

        var station = repo.findById(station_id);

        if(station.isEmpty())
            throw new NoEntriesFoundException();

        return station.get();
    }

    /**
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes.
     */
    @RequestMapping(api_prefix + "stations")
    public List<Station> getStationsBetweenCoordinates(
            @RequestParam(value = "min_latitude", defaultValue = "-1") double min_latitude,
            @RequestParam(value = "max_latitude", defaultValue = "-1") double max_latitude,
            @RequestParam(value = "min_longitude", defaultValue = "-1") double min_longitude,
            @RequestParam(value = "max_longitude", defaultValue = "-1") double max_longitude)
    {
        return repo.findAllByLatitudeBetweenAndLongitudeBetween(min_latitude, max_latitude, min_longitude, max_longitude);
    }

    /**
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes (in pages).
     */
    @RequestMapping(api_prefix + "paged/stations")
    public Page<Station> getStationsBetweenCoordinates(
            Pageable pageable,
            @RequestParam(value = "min_latitude", defaultValue = "-1") double min_latitude,
            @RequestParam(value = "max_latitude", defaultValue = "-1") double max_latitude,
            @RequestParam(value = "min_longitude", defaultValue = "-1") double min_longitude,
            @RequestParam(value = "max_longitude", defaultValue = "-1") double max_longitude)
    {
        return repo.findAllByLatitudeBetweenAndLongitudeBetween(pageable, min_latitude, max_latitude, min_longitude, max_longitude);
    }
}
