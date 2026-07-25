package com.booking.booking.service;

import com.booking.booking.dto.RoomDTO;
import com.booking.booking.dto.UpdateRoomDTO;
import com.booking.booking.exception.RoomNumberAlreadyExistsException;
import com.booking.booking.model.Room;
import com.booking.booking.repository.RoomRepository;
import jakarta.transaction.Transactional;
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
            throw new RoomNumberAlreadyExistsException("Room number " + roomDTO.number() + " already registered");
        }

        Room room = new Room();

        room.setNumber(roomDTO.number());
        room.setCapacity(roomDTO.capacity());
        room.setDescription(roomDTO.description());
        room.setStatus(roomDTO.roomStatus());
        room.setPricePerNight(roomDTO.pricePerNight());

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Room with id="+ id +" Not found"));
    }

    public Room findRoomForUpdate(Long id){
        return roomRepository.findForUpdate(id);
    }

    public void deleteRoomById(Long id){
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    @Transactional
    public Room updateRoomById(Long id, UpdateRoomDTO updateRoomDTO){
        Room room = getRoomById(id);

        if(updateRoomDTO.number() != null) {
            if(!updateRoomDTO.number().equals(room.getNumber())
                    && roomRepository.existsByNumber(updateRoomDTO.number())){
                throw new RuntimeException("Room number " + updateRoomDTO.number() + " already registered");
            }
            room.setNumber(updateRoomDTO.number());
        }


        if(updateRoomDTO.capacity() != null)
            room.setCapacity(updateRoomDTO.capacity());

        if(updateRoomDTO.description() != null)
            room.setDescription(updateRoomDTO.description());

        if(updateRoomDTO.roomStatus() != null)
            room.setStatus(updateRoomDTO.roomStatus());

        if(updateRoomDTO.pricePerNight() != null)
            room.setPricePerNight(updateRoomDTO.pricePerNight());

        return roomRepository.save(room);
    }


}
