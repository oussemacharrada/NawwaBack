package com.dev.nawwa.repository;

import com.dev.nawwa.domain.Location;
import com.dev.nawwa.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {

    List<Location> findByReservation(Reservation reservation);

    Location findByIdAndReservationId(Long id, Long reservationId);

    List<Location> findByReservationId(Long reservationId);
}
