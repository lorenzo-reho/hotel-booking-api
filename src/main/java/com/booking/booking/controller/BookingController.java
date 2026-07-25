package com.booking.booking.controller;

import com.booking.booking.dto.CreateBookingDTO;
import com.booking.booking.model.Booking;
import com.booking.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping({"/", ""})
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @PostMapping({"", "/"})
    public Booking addNewBooking(@Valid @RequestBody CreateBookingDTO createBookingDTO){
        return bookingService.createNewBooking(createBookingDTO);
    }

}
