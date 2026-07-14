package com.booking.booking.service;

import com.booking.booking.dto.RoomDTO;
import com.booking.booking.model.Room;
import com.booking.booking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room addNewRoom(RoomDTO roomDTO){

        if(roomRepository.existsByNumber(roomDTO.number())){
            throw new RuntimeException("Room number " + roomDTO.number() + " already registered");
        }

        Room room = new Room();

        room.setNumber(roomDTO.number());
        room.setCapacity(roomDTO.capacity());
        room.setDescription(roomDTO.description());
        room.setPricePerNight(roomDTO.pricePerNight());

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Room with id="+ id +" Not found"));
    }


}
