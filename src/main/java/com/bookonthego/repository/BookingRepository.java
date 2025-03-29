package com.bookonthego.repository;

import com.bookonthego.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query methods can be added here if needed

    Optional<Booking> findByEventId(Long eventId);

    Optional<Booking> findByUserIdAndEventId(Long userIds, Long eventId);
}
