package com.dravaib.dravaib.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.dravaib.dravaib.facade.QrFacade;
import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.repository.PlaceRepository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    public ResponseEntity<Place> createPlace(@Valid @RequestBody Place place) {
        return ResponseEntity.ok(placeRepository.save(place));
    }

    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "Create qr code from place", tags = { "place" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE)) })
    public @ResponseBody byte[] getQrCode(@PathVariable String id) {

        if (!placeRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found with id: " + id);
        }

        return qrFacade.getQRCodeImage(id);
    }

}
