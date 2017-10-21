package com.taxicalls.trip.repository;

import com.taxicalls.trip.model.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

}
