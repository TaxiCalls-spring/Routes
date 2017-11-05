package com.taxicalls.trip.resources;

import com.taxicalls.protocol.Response;
import com.taxicalls.trip.model.Trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taxicalls.trip.service.TripService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/trips")
public class TripsResource {

    protected static final Logger LOGGER = Logger.getLogger(TripsResource.class.getName());

    @Autowired
    protected TripService tripService;

    @RequestMapping(method = RequestMethod.POST)
    public Response createTrip(@RequestBody Trip trip) {
        Trip createTrip = tripService.createTrip(trip);
        return Response.successful(createTrip);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public Response updateTrip(@RequestBody Trip trip) {
        tripService.updateTrip(trip);
        return Response.successful();
    }

    @RequestMapping
    public Response getTrips() {
        List<Trip> trips = tripService.getTrips();
        return Response.successful(trips);
    }

    @RequestMapping("/{id}")
    public Response getTrip(@PathVariable("id") Long id) {
        LOGGER.log(Level.INFO, "getTrip() invoked: {0}", id);
        Trip trip = tripService.getTrip(id);
        LOGGER.log(Level.INFO, "byId() found: {0}", trip);
        if (trip == null) {
            return Response.notFound();
        } else {
            return Response.successful(trip);
        }
    }
}
