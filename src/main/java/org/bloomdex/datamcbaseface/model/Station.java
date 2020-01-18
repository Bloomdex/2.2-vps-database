package org.bloomdex.datamcbaseface.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String country;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private double elevation;

    //region Constructors

    /**
     * Create an empty default station
     */
    public Station() {
        id = 0;
        name = "default_station";
        country = "NETHERLANDS";
        latitude = 52.3;
        longitude = 4.767;
        elevation = -4;
    }

    /**
     * Create a station object
     * @param name The name of the station.
     * @param country Country this station is located in.
     * @param latitude Latitude of this station.
     * @param longitude Longitude of this station.
     * @param elevation Height of this station in meters.
     */
    public Station(long id, String name, String country, double latitude, double longitude, double elevation) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    //endregion

    //region Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    //endregion
}
