package com.dravaib.dravaib.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dravaib.dravaib.facade.QrFacade;
import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.repository.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private QrFacade qrFacade;

    @GetMapping
    public ResponseEntity<Iterable<Place>> getPlaces() {
        return ResponseEntity.ok(placeRepository.findAll(Sort.by("name")));
    }

    @PostMapping
    public ResponseEntity<Place> createPlace(@Valid @RequestBody Place place) {
        return ResponseEntity.ok(placeRepository.save(place));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPlace(@ModelAttribute Optional<Place> place) {
        if (!place.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(place);
    }

    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getQrCode(@PathVariable String id) {
        return qrFacade.getQRCodeImage(id);
    }

}
