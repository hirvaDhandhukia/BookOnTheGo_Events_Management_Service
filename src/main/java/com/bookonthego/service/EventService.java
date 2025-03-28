package com.bookonthego.service;

import com.bookonthego.model.Booking;
import com.bookonthego.model.Event;
import com.bookonthego.repository.BookingRepository;
import com.bookonthego.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Event createEvent(Event event) {
        // Automatically sync available tickets with total seats at creation
        event.setNoOfTickets(event.getTotalSeats());
        return eventRepository.save(event);
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Long eventId, Event updatedEvent, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent() && eventOptional.get().getCreatorId().equals(userId)) {
            Event event = eventOptional.get();
            event.setName(updatedEvent.getName());
            event.setEventDetails(updatedEvent.getEventDetails());
            event.setDate(updatedEvent.getDate());
            event.setNoOfTickets(updatedEvent.getNoOfTickets());
            event.setTotalSeats(updatedEvent.getTotalSeats());
            return eventRepository.save(event);
        }
        return null;  // or throw exception if needed
    }

//    public String bookTicket(Long eventId, String userId) {
//        Optional<Event> eventOptional = eventRepository.findById(eventId);
//        if (eventOptional.isPresent()) {
//            Event event = eventOptional.get();
//            if (event.getNoOfTickets() > 0) {
//                Booking booking = new Booking();
//                booking.setEventId(eventId);
//                String currentUserIds = bookingRepository.findById(eventId).get().getUserIds();
//                booking.setUserIds(currentUserIds + "," + userId);
//                bookingRepository.save(booking);
//                event.setNoOfTickets(event.getNoOfTickets() - 1);
//                eventRepository.save(event);
//                return "Ticket booked successfully.";
//            }
//        }
//        return "Tickets unavailable.";
//    }

    public String bookTicket(Long eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();

            if (event.getNoOfTickets() > 0) {
                // Fetch all bookings and check if one exists for this event
                List<Booking> existingBookings = bookingRepository.findAll();

                Booking eventBooking = null;
                for (Booking booking : existingBookings) {
                    if (booking.getEventId().equals(eventId)) {
                        eventBooking = booking;
                        break;
                    }
                }

                if (eventBooking == null) {
                    eventBooking = new Booking();
                    eventBooking.setEventId(eventId);
                    eventBooking.setUserIds(userId);
                } else {
                    String currentUserIds = eventBooking.getUserIds();
                    eventBooking.setUserIds(currentUserIds + "," + userId);
                }

                bookingRepository.save(eventBooking);
                event.setNoOfTickets(event.getNoOfTickets() - 1);
                eventRepository.save(event);

                return "Ticket booked successfully.";
            } else {
                return "Tickets unavailable.";
            }
        }
        return "Event not found.";
    }


    public boolean deleteEvent(Long eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent() && eventOptional.get().getCreatorId().equals(userId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }
}

