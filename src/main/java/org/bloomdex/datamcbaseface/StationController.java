package org.bloomdex.datamcbaseface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StationController extends AbstractController {

    @Autowired
    StationRepository repo;

    /**
     * @param authentication The authentication string, should be a string of 48 random characters a-z, A-Z, 0-9
     * @param station_id The id of the station you want to retrieve information from.
     * @return Info about the requested station.
     * @throws InvalidRequestException Will be thrown when client parameters are invalid/
     * @throws InvalidAuthenticationException Will be thrown when insufficient authentication is given.
     * @throws NoEntriesFoundException Will be thrown when no valid station has been returned.
     */
    @RequestMapping(api_prefix + "station_info")
    public Station getStationById(
            @RequestParam(value = "auth", defaultValue = "") String authentication,
            @RequestParam(value = "station_id", defaultValue = "-1") long station_id)
            throws InvalidRequestException, InvalidAuthenticationException, NoEntriesFoundException
    {
        if(!CheckAuthentication(authentication))
            throw new InvalidAuthenticationException();

        if(station_id < 0)
            throw new InvalidRequestException();

        var station = repo.findById(station_id);

        if(station.isEmpty())
            throw new NoEntriesFoundException();

        return station.get();
    }

    /**
     * @param authentication The authentication string, should be a string of 48 random characters a-z, A-Z, 0-9
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes.
     * @throws InvalidAuthenticationException Will be thrown when insufficient authentication is given.
     */
    @RequestMapping(api_prefix + "stations")
    public List<Station> getStationsBetweenCoordinates(
            @RequestParam(value = "auth", defaultValue = "") String authentication,
            @RequestParam(value = "min_latitude", defaultValue = "-1") double min_latitude,
            @RequestParam(value = "max_latitude", defaultValue = "-1") double max_latitude,
            @RequestParam(value = "min_longitude", defaultValue = "-1") double min_longitude,
            @RequestParam(value = "max_longitude", defaultValue = "-1") double max_longitude)
            throws InvalidAuthenticationException
    {
        if(!CheckAuthentication(authentication))
            throw new InvalidAuthenticationException();

        return repo.findStationsByLatitudeBetweenAndLongitudeBetween(min_latitude, max_latitude, min_longitude, max_longitude);
    }
}
