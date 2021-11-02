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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
    public ResponseEntity<Iterable<Visit>> getVisits() {
        return ResponseEntity.ok(visitRepository.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateVisit(@PathVariable Integer id, @Valid @RequestBody UpdateVisitRequest dto) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        visit.setFinishDate(dto.getFinishDate());
        visitRepository.save(visit);
        return ResponseEntity.ok(visit);
    }

    @GetMapping("/current_user")
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
