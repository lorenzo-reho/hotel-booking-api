package com.booking.booking.controller;

import com.booking.booking.dto.CreateBookingDTO;
import com.booking.booking.dto.UpdateBookingDTO;
import com.booking.booking.model.Booking;
import com.booking.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        return bookingService.getBookingById(id, userDetails);
    }

    @GetMapping("/my")
    public List<Booking> getMyBookings(@AuthenticationPrincipal UserDetails userDetails){
        return bookingService.getMyBookings(userDetails);
    }

    @PostMapping
    public Booking addNewBooking(@Valid @RequestBody CreateBookingDTO createBookingDTO){
        return bookingService.createNewBooking(createBookingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookingById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        bookingService.deleteBookingById(id, userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public Booking updateBookingById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @Valid @RequestBody UpdateBookingDTO updateBookingDTO){
        return bookingService.updateBookingById(id, updateBookingDTO, userDetails);
    }

}
