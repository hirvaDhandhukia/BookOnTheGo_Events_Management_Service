package com.bookonthego.DTO;


import java.util.Date;

public class CreateEventRequestDto {

    private String name;
    private String eventDetails;
    private Date date;
    private int noOfTickets;
    private int totalSeats;
    private String images;
    private double price;

    // Default constructor
    public CreateEventRequestDto() {}

    // Constructor with all fields
    public CreateEventRequestDto(String name, String eventDetails, Date date, int noOfTickets, int totalSeats, String images, double price) {
        this.name = name;
        this.eventDetails = eventDetails;
        this.date = date;
        this.noOfTickets = noOfTickets;
        this.totalSeats = totalSeats;
        this.images = images;
        this.price = price;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
