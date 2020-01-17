package org.bloomdex.datamcbaseface;

import java.util.Date;

public class Measurement {

    private final int station_id;
    private final Date date;
    private float temperature;
    private float dew_point;
    private float air_pressure_station;
    private float air_pressure_sea;
    private float visibility;
    private float wind_speed;
    private float wind_direction;
    private float rainfall;
    private float snowfall;
    private short cloud_coverage;

    private boolean freeze;
    private boolean rain;
    private boolean snow;
    private boolean hail;
    private boolean storm;
    private boolean tornado;

    /***
     * Create an empty default measurement
     */
    public Measurement() {
        station_id = 0;
        date = new Date();
        temperature = 0f;
        dew_point = 0f;
        air_pressure_station = 0f;
        air_pressure_sea = 0f;
        visibility = 0f;
        wind_speed = 0f;
        wind_direction = 0f;
        rainfall = 0f;
        snowfall = 0f;
        cloud_coverage = 0;

        freeze = false;
        rain = false;
        snow = false;
        hail = false;
        storm = false;
        tornado = false;
    }

    /**
     * Create a measurement object.
     * @param station_id The id of the station.
     * @param date The date that this measurement has been sent.
     * @param temperature Temperature in degrees celsius.
     * @param dew_point Dew point in degrees celsius.
     * @param air_pressure_station Air pressure at station level in millibars (rounded to 1 decimal).
     * @param air_pressure_sea Air pressure at sea level in millibars (rounded to 1 decimal).
     * @param visibility Visibility in kilometers (rounded to 1 decimal).
     * @param wind_speed Wind speed in kilometers/hour (rounded to 1 decimal).
     * @param wind_direction Wind direction in degrees 0-359
     * @param rainfall Rainfall in centimeters (rounded to 1 decimal).
     * @param snowfall Snowfall in centimeters (rounded to 1 decimal).
     * @param cloud_coverage Cloud coverage in percentages 0.0-100 (rounded to 1 decimal).
     * @param freeze Was it freezing?
     * @param rain Was it raining?
     * @param snow Was it snowing?
     * @param hail Was it hailing?
     * @param storm Was it storming?
     * @param tornado Did a tornado occur?
     */
    public Measurement(int station_id,
                       Date date,
                       float temperature,
                       float dew_point,
                       float air_pressure_station,
                       float air_pressure_sea,
                       float visibility,
                       float wind_speed,
                       float wind_direction,
                       float rainfall,
                       float snowfall,
                       short cloud_coverage,
                       boolean freeze,
                       boolean rain,
                       boolean snow,
                       boolean hail,
                       boolean storm,
                       boolean tornado) {
        this.station_id = station_id;
        this.date = date;
        this.temperature = temperature;
        this.dew_point = dew_point;
        this.air_pressure_station = air_pressure_station;
        this.air_pressure_sea = air_pressure_sea;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.rainfall = rainfall;
        this.snowfall = snowfall;
        this.cloud_coverage = cloud_coverage;
        this.freeze = freeze;
        this.rain = rain;
        this.snow = snow;
        this.hail = hail;
        this.storm = storm;
        this.tornado = tornado;
    }

    /**
     * @return The station id this measurement belongs to
     */
    public int getStation_id() {
        return station_id;
    }

    /**
     * @return The date that this measurement has been sent.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return Measured temperature in degrees celsius.
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * @return Dew point in degrees celsius.
     */
    public float getDew_point() {
        return dew_point;
    }

    /**
     * @return Air pressure at station level in millibars (rounded to 1 decimal).
     */
    public float getAir_pressure_station() {
        return air_pressure_station;
    }

    /**
     * @return Air pressure at sea level in millibars (rounded to 1 decimal).
     */
    public float getAir_pressure_sea() {
        return air_pressure_sea;
    }

    /**
     * @return Visibility in kilometers (rounded to 1 decimal).
     */
    public float getVisibility() {
        return visibility;
    }

    /**
     * @return Wind speed in kilometers/hour (rounded to 1 decimal).
     */
    public float getWind_speed() {
        return wind_speed;
    }

    /**
     * @return Wind direction in degrees 0-359
     */
    public float getWind_direction() {
        return wind_direction;
    }

    /**
     * @return Rainfall in centimeters (rounded to 1 decimal).
     */
    public float getRainfall() {
        return rainfall;
    }

    /**
     * @return Snowfall in centimeters (rounded to 1 decimal).
     */
    public float getSnowfall() {
        return snowfall;
    }

    /**
     * @return Cloud coverage in percentages 0.0-100 (rounded to 1 decimal).
     */
    public short getCloud_coverage() {
        return cloud_coverage;
    }

    /**
     * @return Was it freezing?
     */
    public boolean isFreeze() {
        return freeze;
    }

    /**
     * @return Was it raining?
     */
    public boolean isRain() {
        return rain;
    }

    /**
     * @return Was it snowing?
     */
    public boolean isSnow() {
        return snow;
    }

    /**
     * @return Was it hailing?
     */
    public boolean isHail() {
        return hail;
    }

    /**
     * @return Was it storming?
     */
    public boolean isStorm() {
        return storm;
    }

    /**
     * @return Did a tornado occur?
     */
    public boolean isTornado() {
        return tornado;
    }
}
