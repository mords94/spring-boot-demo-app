package com.dravaib.dravaib.repository;

import java.util.Optional;

import com.dravaib.dravaib.model.Guest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Integer> {
    Optional<Guest> findByPersonDetailsEmail(String email);

}
