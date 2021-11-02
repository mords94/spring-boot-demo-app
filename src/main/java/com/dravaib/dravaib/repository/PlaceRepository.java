package com.dravaib.dravaib.repository;

import java.util.Optional;
import java.util.UUID;

import com.dravaib.dravaib.model.Place;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends PagingAndSortingRepository<Place, UUID> {
    default public Optional<Place> findById(String id) {
        try {
            return findById(UUID.fromString(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}