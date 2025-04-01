package com.bookonthego.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequestDto {
    private String name;
    private String eventDetails;
    private Long date;
    private int noOfTickets;
    private int totalSeats;
    private Double price;
    private String images;
}
