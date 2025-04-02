package com.bookonthego.repository;

import com.bookonthego.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<List<Event>> findByCreatorId(Long creatorId);
    // Custom query methods can be added here if needed
}

