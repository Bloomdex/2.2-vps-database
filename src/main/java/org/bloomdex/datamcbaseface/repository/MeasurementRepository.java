package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {

    /**
     * @param station_id the station id where all measurements should be returned from.
     * @return A List of Measurements from this station.
     */
    Page<Measurement> findMeasurementsByStationId(Pageable pageable, int station_id);

    /**
     * @param pageable The page settings for requesting this info
     * @param firstDate The minimum date
     * @param secondDate The maximum date
     * @return The average measurements for all stations between the given time
     */
    @Query("SELECT new map (stationId as stationId,\n" +
            "       avg(air_pressure_sea) as airPressureSea,\n" +
            "       avg(air_pressure_station) as airPressureStation,\n" +
            "       AVG(cloud_coverage) as cloudCoverage,\n" +
            "       AVG(dew_point) as dewPoint,\n" +
            "       AVG(rainfall) as rainfall,\n" +
            "       AVG(snowfall) as snowfall,\n" +
            "       AVG(temperature) as temperature,\n" +
            "       AVG(visibility) as visibility,\n" +
            "       AVG(wind_direction) as windDirection,\n" +
            "       AVG(wind_speed) as windSpeed \n" +
            ") FROM Measurement WHERE date BETWEEN :firstDate AND :secondDate GROUP BY stationId")
    List<Map<String, Object>> getAverageBetweenDatesGroupByStationId(Pageable pageable, Date firstDate, Date secondDate);

    /**
     * @param firstDate The minimum date
     * @param secondDate The maximum date
     * @param stationId The id of the station the average should be calculated for.
     * @return The average measurement for the given station between the given time.
     */
    @Query("SELECT new map (stationId as stationId,\n" +
            "       avg(air_pressure_sea) as airPressureSea,\n" +
            "       avg(air_pressure_station) as airPressureStation,\n" +
            "       AVG(cloud_coverage) as cloudCoverage,\n" +
            "       AVG(dew_point) as dewPoint,\n" +
            "       AVG(rainfall) as rainfall,\n" +
            "       AVG(snowfall) as snowfall,\n" +
            "       AVG(temperature) as temperature,\n" +
            "       AVG(visibility) as visibility,\n" +
            "       AVG(wind_direction) as windDirection,\n" +
            "       AVG(wind_speed) as windSpeed \n" +
            ") FROM Measurement WHERE date BETWEEN :firstDate AND :secondDate AND stationId=:stationId")
    Map<String, Object> getAverageBetweenDatesByStationId(Date firstDate, Date secondDate, int stationId);
}
