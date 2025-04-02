package com.bookonthego.service;

import com.bookonthego.DTO.BookTicketResponseDto;
import com.bookonthego.DTO.CreateEventRequestDto;
import com.bookonthego.DTO.UpdateEventRequestDto;
import com.bookonthego.model.Booking;
import com.bookonthego.model.Event;
import com.bookonthego.repository.BookingRepository;
import com.bookonthego.repository.EventRepository;
import com.bookonthego.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Event createEvent(CreateEventRequestDto event, String token) {
        Map<String, Object> claimMap = jwtUtil.extractAllClaims(token);
        String userId = claimMap.get("id").toString();

        // check if there is an event with same name on the same date

        List<Event> events = eventRepository.findAll();

        for (Event e : events) {
            if (e.getName().equals(event.getName()) && e.getDate().equals(event.getDate())) {
                throw new IllegalStateException("Event with same name and date already exists.");
            }
        }

        // Automatically sync available tickets with total seats at creation
        event.setNoOfTickets(event.getTotalSeats());
        Event FinalEvent = Event.builder()
                .creatorId(Long.valueOf(userId))
                .name(event.getName())
                .eventDetails(event.getEventDetails())
                .date(event.getDate())
                .price(event.getPrice())
                .noOfTickets(event.getNoOfTickets())
                .images(event.getImages())
                .totalSeats(event.getTotalSeats())
                .build();
        return eventRepository.save(FinalEvent);
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Long eventId, UpdateEventRequestDto updatedEvent, String token) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        Map<String, Object> claimMap = jwtUtil.extractAllClaims(token);
        String userId = claimMap.get("id").toString();


        if (eventOptional.isPresent() && eventOptional.get().getCreatorId().equals(Long.valueOf(userId))) {
            Event event = eventOptional.get();
            event.setName(updatedEvent.getName());
            event.setEventDetails(updatedEvent.getEventDetails());
            event.setDate(updatedEvent.getDate());
            event.setNoOfTickets(updatedEvent.getNoOfTickets());
            event.setTotalSeats(updatedEvent.getTotalSeats());
            event.setPrice(updatedEvent.getPrice());
            event.setImages(updatedEvent.getImages());
            return eventRepository.save(event);
        }
        return null;
    }


    public BookTicketResponseDto bookTicket(Long eventId, Integer numberOfTickets, String token) {
        Map<String, Object> claimMap = jwtUtil.extractAllClaims(token);
        String userId = claimMap.get("id").toString();
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Double price = event.getPrice();
            Double ticketCost;

            if (event.getNoOfTickets() >= numberOfTickets){

                Optional<Booking> bookingOptional = bookingRepository.findByUserIdAndEventId(Long.valueOf(userId),eventId);
                Booking booking;
                if(bookingOptional.isPresent()) {
                    booking = bookingOptional.get();
                    booking.setNoOfTickets(numberOfTickets + booking.getNoOfTickets());
                }
                else {
                    booking = new Booking();
                    booking.setEventId(eventId);
                    booking.setUserId(Long.valueOf(userId));
                    booking.setNoOfTickets(numberOfTickets);
                }
                ticketCost = price * numberOfTickets;
                try {
                    bookingRepository.save(booking);
                } catch (Exception e) {
                    throw new IllegalStateException("Booking failed.");
                }

                event.setNoOfTickets(event.getNoOfTickets() - numberOfTickets);
                eventRepository.save(event);
                return BookTicketResponseDto.builder()
                        .bookingId(booking.getBookingId())
                        .eventId(eventId)
                        .price(ticketCost)
                        .noOfTickets(numberOfTickets)
                        .totalNumberOfTickets(booking.getNoOfTickets())
                        .build();
            } else {
                throw new IllegalStateException("Tickets unavailable.");
            }
        }
        throw new NoSuchElementException("Event not found.");
    }


    public boolean deleteEvent(Long eventId, String token) {
        Map<String, Object> claimMap = jwtUtil.extractAllClaims(token);
        String userId = claimMap.get("id").toString();
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent() && eventOptional.get().getCreatorId().equals(Long.valueOf(userId))) {
            eventRepository.deleteById(eventId);
            bookingRepository.deleteBookingByEventId(eventId);
            return true;
        }
        return false;
    }

    public Optional<List<Event>> getMyEvents(String token) {
        Map<String, Object> claimMap = jwtUtil.extractAllClaims(token);
        String userId = claimMap.get("id").toString();
        return eventRepository.findByCreatorId(Long.valueOf(userId));
    }
}

