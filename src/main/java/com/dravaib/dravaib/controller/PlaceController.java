package com.dravaib.dravaib.controller;

import javax.validation.Valid;

import com.dravaib.dravaib.facade.QrFacade;
import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.repository.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private QrFacade qrFacade;

    @GetMapping
    @Operation(summary = "Lists all places", tags = { "place" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class)))) })
    public ResponseEntity<Iterable<Place>> getPlaces() {
        return ResponseEntity.ok(placeRepository.findAll(Sort.by("name")));
    }

    @PostMapping
    @Operation(summary = "Creates a new place", tags = { "place" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Place> createPlace(@Valid @RequestBody Place place) {
        return new ResponseEntity<Place>(placeRepository.save(place), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates a place", tags = { "place" })
    public ResponseEntity<Void> updatePlace(@Valid @RequestBody Place place) {
        placeRepository.save(place);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "Create qr code for a place", tags = { "place" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE)) })
    public @ResponseBody byte[] getQrCode(@PathVariable String id) {

        if (!placeRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found with id: " + id);
        }

        return qrFacade.getQRCodeImage(id);
    }

}
