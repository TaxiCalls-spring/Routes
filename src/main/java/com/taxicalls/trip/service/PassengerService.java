package com.taxicalls.trip.service;

import com.taxicalls.trip.model.Passenger;
import com.taxicalls.trip.repository.PassengerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
@Service
public class PassengerService {

    protected static final Logger LOGGER = Logger.getLogger(PassengerService.class.getName());

    protected PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
        LOGGER.log(Level.INFO, "PassengerRepository says system has {0} ", passengerRepository.count());
    }

    public Passenger createPassenger(Passenger passenger) {
        Passenger save = passengerRepository.save(passenger);
        return save;
    }

    public List<Passenger> getPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        Iterable<Passenger> findAll = passengerRepository.findAll();
        for (Passenger passenger : findAll) {
            passengers.add(passenger);
        }
        return passengers;
    }

    public Passenger getPassenger(Integer id) {
        return passengerRepository.findOne(id);
    }

}
