package org.bloomdex.server;

import org.bloomdex.datamcbaseface.model.Measurement;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    private Converter() {}

    /**
     * Converts a DataInputStream to a list of measurements
     * @param dataInputStream The DataInputStream measurements should be retrieved from
     * @return A List of measurements
     * @throws IOException
     */
    public static List<Measurement> InputStreamToMeasurements(DataInputStream dataInputStream) throws IOException {
        if(dataInputStream.available() % 47 == 0) {
            ArrayList<Measurement> measurements = new ArrayList<>();

            for (int i = 0; i < dataInputStream.available() / 47; i++) {
                var measurement = new Measurement();

                // Station ID
                measurement.setStationId(dataInputStream.readInt());

                // Date and time
                var date = new java.util.Date((long)dataInputStream.readInt()*1000);
                measurement.setDate(date);

                // Temperature
                measurement.setTemperature(dataInputStream.readFloat());

                // Dew point
                measurement.setDew_point(dataInputStream.readFloat());

                // Pressure Station
                measurement.setAir_pressure_station(dataInputStream.readFloat());

                // Pressure Sea
                measurement.setAir_pressure_sea(dataInputStream.readFloat());

                // Visibility
                measurement.setVisibility(dataInputStream.readFloat());

                // Windspeed
                measurement.setWind_speed(dataInputStream.readFloat());

                // Rainfall
                measurement.setRainfall(dataInputStream.readFloat());

                // Snow
                measurement.setSnowfall(dataInputStream.readFloat());

                // Options
                var events = dataInputStream.readByte();
                if ((events & (1 << 0)) == (1 << 0));
                if ((events & (1 << 1)) == (1 << 1));
                measurement.setFreeze(  (events & (1 << 2)) == (1 << 2));
                measurement.setRain(    (events & (1 << 3)) == (1 << 3));
                measurement.setSnow(    (events & (1 << 4)) == (1 << 4));
                measurement.setHail(    (events & (1 << 5)) == (1 << 5));
                measurement.setStorm(   (events & (1 << 6)) == (1 << 6));
                measurement.setTornado( (events & (1 << 7)) == (1 << 7));

                // Cloud coverage
                measurement.setCloud_coverage(dataInputStream.readFloat());

                // Wind speed
                measurement.setWind_direction(dataInputStream.readShort());

                // Add this measurement to the list
                measurements.add(measurement);
            }

            dataInputStream.close();

            return measurements;
        }

        throw new IllegalArgumentException("The file size is not equals to (filesize % 47 == 0)");
    }

}
