package com.bookonthego.DTO;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookTicketResponseDto {
    private Long bookingId;
    private Long eventId;
    private Double price;
    private int noOfTickets;
    private int totalNumberOfTickets;
}
