package com.bookonthego.controller;

import com.bookonthego.model.Event;
import com.bookonthego.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestHeader ("Authorization") String token) {
        Event createdEvent = eventService.createEvent(event, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        Optional<Event> event = eventService.getEvent(eventId);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent, @RequestHeader("Authorization") String token) {
        String userId = "extractedFromJWT";  // Implement JWT extraction logic here
        Event event = eventService.updateEvent(eventId, updatedEvent, userId);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

//    @PostMapping("/{eventId}/book")
//    public ResponseEntity<String> bookTicket(@PathVariable Long eventId, @RequestHeader("Authorization") String token) {
//        String userId = "extractedFromJWT";  // Implement JWT extraction logic here
//        String message = eventService.bookTicket(eventId, userId);
//        return ResponseEntity.ok(message);
////        else if(originalEvent!=null){
////            int noOfTickets = 0; //get no of tickets from request body
////            originalEvent.setNoOfTickets(originalEvent.getNoOfTickets()-noOfTickets);
////            Event event = eventService.updateEvent(eventId, updatedEvent, originalEvent.getCreatorId());
////            return event != null ? ResponseEntity.ok(event) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
////        }
//    }

    @PostMapping("/{eventId}/book")
    public ResponseEntity<?> bookTicket(@PathVariable Long eventId, @RequestHeader("Authorization") String token) {
        String userId = "extractedFromJWT"; // replace with JWT logic later

        try {
            Map<String, Object> result = eventService.bookTicket(eventId, userId);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId, @RequestHeader("Authorization") String token) {
        String userId = "extractedFromJWT";  // Implement JWT extraction logic here
        boolean deleted = eventService.deleteEvent(eventId, userId);
        return deleted ? ResponseEntity.ok("Event deleted.") : ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized.");
    }
}

