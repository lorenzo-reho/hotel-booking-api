package com.booking.booking.service;

import com.booking.booking.dto.RegisterRequest;
import com.booking.booking.model.User;
import com.booking.booking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserService userService;

    public AuthService(UserService userService){
        this.userService = userService;
    }

    public void register(RegisterRequest registerRequest){
        userService.addNewUser(registerRequest);
    }

}
