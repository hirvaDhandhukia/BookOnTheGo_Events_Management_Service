package com.bookonthego.repository;

import com.bookonthego.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query methods can be added here if needed
}

