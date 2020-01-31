package org.bloomdex.datamcbaseface.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "measurement", indexes = {@Index(columnList = "date", name = "date_hidx"), @Index(columnList = "stationId", name = "stationId_hidx")})
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int stationId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private float temperature;
    private float dew_point;
    private float air_pressure_station;
    private float air_pressure_sea;
    private float visibility;
    private float wind_speed;
    private float wind_direction;
    private float rainfall;
    private float snowfall;
    private float cloud_coverage;

    private boolean freeze;
    private boolean rain;
    private boolean snow;
    private boolean hail;
    private boolean storm;
    private boolean tornado;

    //region Constructors

    /***
     * Create an empty default measurement
     */
    public Measurement() {
        stationId = 0;
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
     * @param stationId The id of the station.
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
    public Measurement(int stationId,
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
                       float cloud_coverage,
                       boolean freeze,
                       boolean rain,
                       boolean snow,
                       boolean hail,
                       boolean storm,
                       boolean tornado) {
        this.stationId = stationId;
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

    //endregion

    //region Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId =  stationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getDew_point() {
        return dew_point;
    }

    public void setDew_point(float dew_point) {
        this.dew_point = dew_point;
    }

    public float getAir_pressure_station() {
        return air_pressure_station;
    }

    public void setAir_pressure_station(float air_pressure_station) {
        this.air_pressure_station = air_pressure_station;
    }

    public float getAir_pressure_sea() {
        return air_pressure_sea;
    }

    public void setAir_pressure_sea(float air_pressure_sea) {
        this.air_pressure_sea = air_pressure_sea;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }

    public float getRainfall() {
        return rainfall;
    }

    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }

    public float getSnowfall() {
        return snowfall;
    }

    public void setSnowfall(float snowfall) {
        this.snowfall = snowfall;
    }

    public float getCloud_coverage() {
        return cloud_coverage;
    }

    public void setCloud_coverage(float cloud_coverage) {
        this.cloud_coverage = cloud_coverage;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public boolean isSnow() {
        return snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
    }

    public boolean isHail() {
        return hail;
    }

    public void setHail(boolean hail) {
        this.hail = hail;
    }

    public boolean isStorm() {
        return storm;
    }

    public void setStorm(boolean storm) {
        this.storm = storm;
    }

    public boolean isTornado() {
        return tornado;
    }

    public void setTornado(boolean tornado) {
        this.tornado = tornado;
    }

    //endregion

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", stationId=" + stationId +
                ", date=" + date +
                ", temperature=" + temperature +
                ", dew_point=" + dew_point +
                ", air_pressure_station=" + air_pressure_station +
                ", air_pressure_sea=" + air_pressure_sea +
                ", visibility=" + visibility +
                ", wind_speed=" + wind_speed +
                ", wind_direction=" + wind_direction +
                ", rainfall=" + rainfall +
                ", snowfall=" + snowfall +
                ", cloud_coverage=" + cloud_coverage +
                ", freeze=" + freeze +
                ", rain=" + rain +
                ", snow=" + snow +
                ", hail=" + hail +
                ", storm=" + storm +
                ", tornado=" + tornado +
                '}';
    }
}
