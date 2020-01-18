package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepository extends CrudRepository<Station, Long> {
    /**
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes.
     */
    List<Station> findStationsByLatitudeBetweenAndLongitudeBetween(double min_latitude, double max_latitude, double min_longitude, double max_longitude);
}
