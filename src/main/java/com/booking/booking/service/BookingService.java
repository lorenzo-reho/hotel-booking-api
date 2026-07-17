package com.booking.booking.service;

import com.booking.booking.model.Booking;
import com.booking.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking with id=" + id + " Not exists"));
    }



}
