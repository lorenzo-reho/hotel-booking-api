package com.booking.booking.controller;

import com.booking.booking.dto.RoomDTO;
import com.booking.booking.model.Room;
import com.booking.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping({"/", ""})
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addNewRoom(@Valid @RequestBody RoomDTO roomDTO){
        roomService.addNewRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
