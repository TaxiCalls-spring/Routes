package com.taxicalls.trip.repository;

import com.taxicalls.trip.model.Passenger;
import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {

}
