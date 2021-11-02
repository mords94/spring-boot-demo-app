package com.dravaib.dravaib.repository;

import java.util.Optional;

import com.dravaib.dravaib.model.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByPersonDetailsEmail(String email);

    boolean existsByPersonDetailsEmail(String email);

    Optional<User> findByPersonDetailsEmailAndPassword(String email, String password);
}