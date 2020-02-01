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
            "       AVG(air_pressure_sea) as airPressureSea,\n" +
            "       AVG(air_pressure_station) as airPressureStation,\n" +
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
            "       AVG(air_pressure_sea) as airPressureSea,\n" +
            "       AVG(air_pressure_station) as airPressureStation,\n" +
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

    /**
     * @param stationId The station averages should be retrieved from.
     * @return Averages per day from last month of the given station.
     */
    @Query("SELECT new map (stationId as stationId,\n" +
            "EXTRACT (YEAR FROM DATE) AS year,\n" +
            "EXTRACT (MONTH FROM DATE) AS month,\n" +
            "EXTRACT (DAY FROM DATE) AS day,\n" +
            "AVG(air_pressure_sea) as airPressureSea,\n" +
            "AVG(air_pressure_station) as airPressureStation,\n" +
            "AVG(cloud_coverage) as cloudCoverage,\n" +
            "AVG(dew_point) as dewPoint,\n" +
            "AVG(rainfall) as rainfall,\n" +
            "AVG(snowfall) as snowfall,\n" +
            "AVG(temperature) as temperature,\n" +
            "AVG(visibility) as visibility,\n" +
            "AVG(wind_direction) as windDirection,\n" +
            "AVG(wind_speed) as windSpeed\n" +
            ") FROM Measurement\n" +
            "WHERE STATION_ID = :stationId AND date >= DATEADD('DAY', -30, CURRENT_DATE)\n" +
            "GROUP BY year, month, day\n")
    List<Map<String, Object>> getAverageLastMonthGroupByDayByStationId(int stationId);

    /**
     * @param stationId The station averages should be retrieved from.
     * @return Stripped averages per spec according to requirements per day from last month of the given station.
     */
    @Query("SELECT new map (\n" +
            "EXTRACT (YEAR FROM DATE) AS year,\n" +
            "EXTRACT (MONTH FROM DATE) AS month,\n" +
            "EXTRACT (DAY FROM DATE) AS day,\n" +
            "AVG(dew_point) as avgDewPoint,\n" +
            "AVG(temperature) as avgTemperature,\n" +
            "MIN(temperature) as minTemperature,\n" +
            "MAX(temperature) as maxTemperature,\n" +
            "AVG(rainfall) as avgRainfall,\n" +
            "MAX(rainfall) as maxRainfall,\n" +
            "MIN(rainfall) as minRainfall)\n" +
            "FROM Measurement\n" +
            "WHERE STATION_ID = :stationId AND date >= DATEADD('DAY', -30, CURRENT_DATE)\n" +
            "GROUP BY year, month, day\n")
    List<Map<String, Object>> getVegaflorSpecificationsLastMonthByStationId(int stationId);

    /**
     * @return The most desirable circumstances for flowers to grow in south-america according to Vegaflor's specification.
     */
    @Query(value = "SELECT new map (stationId as StationId,\n" +
            "       AVG(dew_point) as avgDewPoint,\n" +
            "       AVG(rainfall) as avgRainfall,\n" +
            "       MAX(rainfall) as maxRainfall,\n" +
            "       MIN(rainfall) as minRainfall,\n" +
            "       AVG(temperature) as avgTemperature,\n" +
            "       MAX(temperature) as maxTemperature,\n" +
            "       MIN(temperature) as minTemperature,\n" +
            "       100 * (EXP((17.625 * AVG(dew_point)) / (243.04 + AVG(dew_point))) / EXP((17.625 * AVG(temperature)) / (243.04 + AVG(temperature)))) as avgHumidity )\n" +
            "FROM Measurement \n" +
            "WHERE date >= DATEADD('DAY', -30, CURRENT_DATE)\n" +
            "  AND stationId IN (\n" +
            "      SELECT new map (id as stationId )\n" +
            "          FROM Station\n" +
            "          WHERE latitude BETWEEN -9.752 AND 16.215\n" +
            "          AND longitude BETWEEN -84.375 AND -61.831)\n" +
            "GROUP BY stationId\n" +
            "ORDER BY ABS(100 * (EXP((17.625 * AVG(dew_point)) / (243.04 + AVG(dew_point))) / EXP((17.625 * AVG(temperature)) / (243.04 + AVG(temperature)))) - 80),\n" +
            "         ABS(AVG(temperature) - 22.5)\n")
    List<Map<String, Object>> getVegaflorMostDesirableByHumidityAndTemperature(Pageable page);
}
