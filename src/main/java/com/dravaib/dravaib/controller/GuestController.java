package com.dravaib.dravaib.controller;

import javax.validation.Valid;

import com.dravaib.dravaib.model.Guest;
import com.dravaib.dravaib.repository.GuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    @Autowired
    private GuestRepository guestRepository;

    @GetMapping
    @Operation(summary = "Lists all guests", tags = { "guest" })
    public ResponseEntity<Page<Guest>> getGuests(Pageable pageable) {
        return ResponseEntity.ok(guestRepository.findAll(pageable));
    }

    @PostMapping
    @Operation(summary = "Creates a guests", tags = { "guest" })
    @PreAuthorize("@authorizationService.isAdmin()")
    public ResponseEntity<Guest> createGuest(@Valid Guest guest) {
        return ResponseEntity.ok(guestRepository.save(guest));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates a guests", tags = { "guest" })
    @PreAuthorize("@authorizationService.isAdmin()")
    public ResponseEntity<Guest> updateGuest(@RequestParam int id, @Valid @RequestBody Guest guest) {
        guest.setId(id);
        return ResponseEntity.ok(guestRepository.save(guest));
    }
}
