package com.taxicalls.trip.resources;

import com.taxicalls.protocol.Response;
import com.taxicalls.trip.model.Passenger;
import com.taxicalls.trip.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/passengers")
public class PassengersResource {

    protected static final Logger LOGGER = Logger.getLogger(PassengersResource.class.getName());

    @Autowired
    protected PassengerService passengerService;

    @RequestMapping(method = RequestMethod.POST)
    public Response createPassenger(@RequestBody Passenger passenger) {
        Passenger createPassenger = passengerService.createPassenger(passenger);
        return Response.successful(createPassenger);
    }

    @RequestMapping
    public Response getPassengers() {
        return Response.successful(passengerService.getPassengers());
    }

    @RequestMapping("/{id}")
    public Response getPassenger(@PathVariable("id") Long id) {
        LOGGER.log(Level.INFO, "getPassenger() invoked: {0}", id);
        Passenger passenger = passengerService.getPassenger(id);
        LOGGER.log(Level.INFO, "byId() found: {0}", passenger);
        if (passenger == null) {
            return Response.notFound();
        }
        return Response.successful(passenger);
    }
}
