package com.booking.booking.service;

import com.booking.booking.model.User;
import com.booking.booking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id=" + id + " Not found"));
    }


}
