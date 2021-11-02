package com.dravaib.dravaib.controller;

import com.dravaib.dravaib.model.Guest;
import com.dravaib.dravaib.repository.GuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    @Autowired
    private GuestRepository guestRepository;

    @GetMapping
    public ResponseEntity<Iterable<Guest>> getGuests() {
        return ResponseEntity.ok(guestRepository.findAll());
    }
}
