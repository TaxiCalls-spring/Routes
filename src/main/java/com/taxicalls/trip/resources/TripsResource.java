package com.taxicalls.trip.resources;

import com.taxicalls.trip.model.Trip;
import com.taxicalls.trip.resources.exceptions.TripNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taxicalls.trip.service.TripService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/trips")
public class TripsResource {

    protected static final Logger LOGGER = Logger.getLogger(TripsResource.class.getName());

    @Autowired
    protected TripService tripService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Trip createTrip(Trip trip) {
        return tripService.createTrip(trip);
    }

    @RequestMapping("/")
    public List<Trip> getTrips() {
        return tripService.getTrips();
    }

    @RequestMapping("/{id}")
    public Trip getTrip(@PathVariable("id") Integer id) {
        LOGGER.log(Level.INFO, "getTrip() invoked: {0}", id);
        Trip trip = tripService.getTrip(id);
        LOGGER.log(Level.INFO, "byId() found: {0}", trip);
        if (trip == null) {
            throw new TripNotFoundException(id);
        } else {
            return trip;
        }
    }
}
