package com.dravaib.dravaib.controller;

import com.dravaib.dravaib.model.User;
import com.dravaib.dravaib.repository.UserRepository;
import com.dravaib.dravaib.seed.MainSeed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/dev")
public class DeveloperController {

    @Autowired
    private MainSeed seed;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bEncoder;

    @GetMapping("/seed")
    @Operation(summary = "Populates the database with predefined users and roles", tags = { "dev" }, hidden = true)
    public ResponseEntity<?> populateSeeds() {

        seed.run();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/encrypt/{password}")
    @Operation(summary = "Password magic", tags = { "dev" }, hidden = true)
    public String encrypt(@PathVariable String password) {
        String pw = bEncoder.encode("12345678");
        for (User user : userRepository.findAll()) {
            user.setPassword(pw);

            userRepository.save(user);
        }
        return pw;
    }
}
