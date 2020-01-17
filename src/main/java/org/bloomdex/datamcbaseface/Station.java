package org.bloomdex.datamcbaseface;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Station {
    @Id
    private long id;

    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private double elevation;

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

    /**
     * @return The id of this station.
     */
    public long getId() {
        return id;
    }

    /**
     * @return The name of this station.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The country this station resides in
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return The latitude of this station.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The Longitude of this station.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return Height of this station in meters.
     */
    public double getElevation() {
        return elevation;
    }
}
