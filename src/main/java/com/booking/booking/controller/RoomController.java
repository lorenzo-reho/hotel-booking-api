package com.booking.booking.controller;

import com.booking.booking.dto.RoomDTO;
import com.booking.booking.dto.UpdateRoomDTO;
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

    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }

    @PostMapping
    public Room addNewRoom(@Valid @RequestBody RoomDTO roomDTO){
        return roomService.addNewRoom(roomDTO);
        // return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public Room updateRoomById(@PathVariable Long id, @Valid @RequestBody UpdateRoomDTO roomDTO){
        return roomService.updateRoomById(id, roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRoomById(@PathVariable Long id){
        roomService.deleteRoomById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
