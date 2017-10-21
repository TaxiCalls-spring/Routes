package com.taxicalls.trip.resources;

import com.taxicalls.trip.model.Passenger;
import com.taxicalls.trip.resources.exceptions.PassengerNotFoundException;
import com.taxicalls.trip.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/passengers")
public class PassengersResource {

    protected static final Logger LOGGER = Logger.getLogger(PassengersResource.class.getName());

    @Autowired
    protected PassengerService passengerService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Passenger createPassenger(Passenger passenger) {
        return passengerService.createPassenger(passenger);
    }

    @RequestMapping("/")
    public List<Passenger> getPassengers() {
        return passengerService.getPassengers();
    }

    @RequestMapping("/{id}")
    public Passenger getPassenger(@PathVariable("id") Integer id) {
        LOGGER.log(Level.INFO, "getPassenger() invoked: {0}", id);
        Passenger passenger = passengerService.getPassenger(id);
        LOGGER.log(Level.INFO, "byId() found: {0}", passenger);
        if (passenger == null) {
            throw new PassengerNotFoundException(id);
        } else {
            return passenger;
        }
    }
}
