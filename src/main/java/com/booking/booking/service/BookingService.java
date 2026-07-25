package com.booking.booking.service;

import com.booking.booking.dto.CreateBookingDTO;
import com.booking.booking.exception.RoomAlreadyBookedException;
import com.booking.booking.exception.RoomNumberAlreadyExistsException;
import com.booking.booking.model.Booking;
import com.booking.booking.model.Room;
import com.booking.booking.model.User;
import com.booking.booking.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private UserService userService;
    private RoomService roomService;


    public BookingService(BookingRepository bookingRepository, UserService userService, RoomService roomService){
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking with id=" + id + " Not exists"));
    }

    @Transactional
    public Booking createNewBooking(CreateBookingDTO createBookingDTO){
        Long roomId = createBookingDTO.roomId();
        Long userId = createBookingDTO.userId();

        LocalDate startDate = createBookingDTO.startDate();
        LocalDate endDate = createBookingDTO.endDate();

        User user = userService.getUserById(userId);
        Room room = roomService.findRoomForUpdate(roomId); // lock

        if(!room.getStatus().equals(Room.RoomStatus.AVAILABLE)){
            throw new IllegalStateException("Room is not available");
        }

        if (bookingRepository.existsOverlappingBooking(roomId, startDate, endDate)) {
            throw new RoomAlreadyBookedException(
                    "Room is already booked for the selected dates.");
        }

        Booking booking = new Booking();

        booking.setRoom(room);
        booking.setUser(user);

        booking.setStartDate(createBookingDTO.startDate());
        booking.setEndDate(createBookingDTO.endDate());

        return bookingRepository.save(booking);
    }

}
