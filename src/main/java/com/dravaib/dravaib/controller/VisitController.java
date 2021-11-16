package com.dravaib.dravaib.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.dravaib.dravaib.config.JwtTokenUtil;
import com.dravaib.dravaib.model.Guest;
import com.dravaib.dravaib.model.Visit;
import com.dravaib.dravaib.model.dto.request.CreateVisitRequest;
import com.dravaib.dravaib.model.dto.request.UpdateVisitRequest;
import com.dravaib.dravaib.repository.GuestRepository;
import com.dravaib.dravaib.repository.PlaceRepository;
import com.dravaib.dravaib.repository.UserRepository;
import com.dravaib.dravaib.repository.VisitRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.HttpStatus;

@Validated
@RestController()
@RequestMapping("/api/visit")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    @Operation(summary = "Lists all visits", tags = { "visit" })
    public ResponseEntity<Page<Visit>> getVisits(Pageable pageable) {
        return ResponseEntity.ok(visitRepository.findAll(pageable));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates a visit", description = "Only a check-out date can be set ", tags = { "visit" })
    public ResponseEntity<?> updateVisit(@PathVariable Integer id, @Valid @RequestBody UpdateVisitRequest dto) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        visit.setFinishDate(dto.getFinishDate());
        visitRepository.save(visit);
        return ResponseEntity.ok(visit);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a visit", tags = { "visit" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteVisit(@PathVariable Integer id) {
        visitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current_user")
    @Operation(summary = "Fetches an in-progress visit for the current logged in user", tags = { "visit" })
    public ResponseEntity<Visit> getVisit(@RequestHeader("Authorization") String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        var user = userRepository.findByPersonDetailsEmail(email).get();

        Guest guest = guestRepository.findByPersonDetailsEmail(user.getPersonDetails().getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest with this e-mail not found"));

        var visits = guest.getVisits();

        Visit visit = visits.stream().filter(v -> v.getFinishDate() == null).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found"));

        return ResponseEntity.ok(visit);
    }

    @GetMapping("/current_user/all")
    @Operation(summary = "Lists all visits for the current logged in user", tags = { "visit" })
    public ResponseEntity<Collection<Visit>> getVisits(@RequestHeader("Authorization") String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        var user = userRepository.findByPersonDetailsEmail(email).get();

        Guest guest = guestRepository.findByPersonDetailsEmail(user.getPersonDetails().getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest with this e-mail not found"));

        var visits = guest.getVisits();

        return ResponseEntity.ok(visits);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new visit", tags = { "visit" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createVisit(@Valid @RequestBody CreateVisitRequest dto) {
        Visit visit = new Visit();
        List<Guest> guestsToSave = new ArrayList<Guest>();

        for (Guest guest : dto.getGuests()) {
            Optional<Guest> guestInDatabase = guestRepository
                    .findByPersonDetailsEmail(guest.getPersonDetails().getEmail());

            if (guestInDatabase.isPresent()) {
                guestsToSave.add(guestInDatabase.get());
            } else {
                guestsToSave.add(guestRepository.save(guest));
            }
        }
        visit.setGuests(guestsToSave);
        visit.setPlace(placeRepository.findById(dto.getPlace().getId()).get());

        visitRepository.save(visit);

        return new ResponseEntity<Visit>(visit, HttpStatus.CREATED);
    }
}
