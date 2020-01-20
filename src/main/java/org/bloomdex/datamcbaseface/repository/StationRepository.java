package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StationRepository extends PagingAndSortingRepository<Station, Long> {
    /**
     * @param pageable The pagination settings.
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes (in pages).
     */
    Page<Station> findAllByLatitudeBetweenAndLongitudeBetween(Pageable pageable, double min_latitude, double max_latitude, double min_longitude, double max_longitude);

    /**
     * @param min_latitude The minimum latitude the weather-station should be between.
     * @param max_latitude The maximum latitude the weather-station should be between.
     * @param min_longitude The minimum longitude the weather-station should be between.
     * @param max_longitude The maximum longitude the weather-station should be between.
     * @return A list of weather-stations between the given latitudes and longitudes.
     */
    List<Station> findAllByLatitudeBetweenAndLongitudeBetween(double min_latitude, double max_latitude, double min_longitude, double max_longitude);

}
