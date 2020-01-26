package org.bloomdex.datamcbaseface.repository;

import org.bloomdex.datamcbaseface.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {

    /**
     * @param station_id the station id where all measurements should be returned from.
     * @return A List of Measurements from this station.
     */
    Page<Measurement> findMeasurementsByStationId(Pageable pageable, int station_id);
}
