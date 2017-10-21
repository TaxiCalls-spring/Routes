package com.taxicalls.trip.repository;

import com.taxicalls.trip.model.Trip;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, Integer> {

}
