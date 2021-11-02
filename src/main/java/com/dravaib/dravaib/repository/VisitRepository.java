package com.dravaib.dravaib.repository;

import com.dravaib.dravaib.model.Visit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, Integer> {
}
