package com.booking.booking.service;

import com.booking.booking.dto.RegisterRequest;

import com.booking.booking.dto.UpdateRoomDTO;
import com.booking.booking.dto.UpdateUserDTO;
import com.booking.booking.exception.EmailAlreadyExistsException;
import com.booking.booking.model.Room;
import com.booking.booking.model.User;
import com.booking.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id=" + id + " Not found"));
    }

    @Transactional
    public void deleteUserById(Long id){
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User addNewUser(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new EmailAlreadyExistsException("Email " + registerRequest.email() + " already registered");
        }

        User user = new User();

        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setEmail(registerRequest.email());

        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));

        return userRepository.save(user);
    }
    @Transactional
    public User updateUserById(Long id, UpdateUserDTO updateUserDTO){
        User user = getUserById(id);

        if(updateUserDTO.firstName() != null)
            user.setFirstName(updateUserDTO.firstName());

        if(updateUserDTO.lastName() != null)
            user.setLastName(updateUserDTO.lastName());

        if(updateUserDTO.role() != null)
            user.setRole(updateUserDTO.role());

        return userRepository.save(user);
    }

    public User getUserByPrincipal(UserDetails userDetails){
        String email = userDetails.getUsername();

        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User with email=" + email + " Not found"));
    }


}
