package com.booking.booking.service;

import com.booking.booking.dto.CreateBookingDTO;
import com.booking.booking.dto.UpdateBookingDTO;
import com.booking.booking.exception.ResourceAccessException;
import com.booking.booking.exception.RoomAlreadyBookedException;
import com.booking.booking.model.Booking;
import com.booking.booking.model.Room;
import com.booking.booking.model.User;
import com.booking.booking.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public Booking getBookingById(Long id, UserDetails userDetails){

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Booking with id=" + id + " Not exists"));

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());


        if(roles.contains("ROLE_ADMIN"))
            return booking;

        User user = userService.getUserByPrincipal(userDetails);
        Long userIdFromBooking = booking.getUser().getId();

        if(user.getId().equals(userIdFromBooking))
            return booking;

        throw new ResourceAccessException("You do not have permission to view this booking");
    }

    public List<Booking> getMyBookings(UserDetails userDetails){
        String email = userDetails.getUsername();
        return bookingRepository.findByUserEmail(email);
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

        // bookingId = -1L perché non esiste ancora
        if (bookingRepository.existsOverlappingBooking(-1L, roomId, startDate, endDate)) {
            throw new RoomAlreadyBookedException(
                    "Room is already booked for the selected dates.");
        }

        Booking booking = new Booking();

        booking.setRoom(room);
        booking.setUser(user);

        booking.setStartDate(startDate);
        booking.setEndDate(endDate);

        return bookingRepository.save(booking);
    }

    public void deleteBookingById(Long id, UserDetails userDetails){
        Booking booking = getBookingById(id, userDetails);
        bookingRepository.delete(booking);
    }

    @Transactional
    public Booking updateBookingById(Long bookingId, UpdateBookingDTO updateBookingDTO, UserDetails userDetails){
        Booking booking = getBookingById(bookingId, userDetails);
        Room room = booking.getRoom();

        Long roomId = room.getId();

        LocalDate startDate = updateBookingDTO.startDate();
        LocalDate endDate = updateBookingDTO.endDate();

        if (bookingRepository.existsOverlappingBooking(bookingId, roomId, startDate, endDate)) {
            throw new RoomAlreadyBookedException(
                    "Room is already booked for the selected dates.");
        }

        booking.setStartDate(startDate);
        booking.setEndDate(endDate);

        return bookingRepository.save(booking);
    }

}
