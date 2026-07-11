package com.booking.booking;

import com.booking.booking.model.User;
import com.booking.booking.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private UserService userService;

    ApiController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }


}
