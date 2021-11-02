package com.dravaib.dravaib.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dravaib.dravaib.config.JwtTokenUtil;
import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.model.dto.request.UpdateProfileRequest;
import com.dravaib.dravaib.repository.UserRepository;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Logger logger;

    @GetMapping
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(userRepository.findByPersonDetailsEmail(email).get());
    }

    @PatchMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdateProfileRequest dto) {
        String email = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        var user = userRepository.findByPersonDetailsEmail(email).get();
        user.setPersonDetails(dto.getPersonDetails());

        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User dto) {
        User user = userRepository.save(dto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

}