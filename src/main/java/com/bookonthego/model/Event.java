package com.bookonthego.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private Long creatorId;
    private String name;
    private String eventDetails;
    private Date date;
    private double price;
    private int noOfTickets;
    private String images;
    private int totalSeats;
}
