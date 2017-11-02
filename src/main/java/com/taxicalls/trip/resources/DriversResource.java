package com.taxicalls.trip.resources;

import com.taxicalls.protocol.Response;
import com.taxicalls.trip.model.Coordinate;
import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/drivers")
public class DriversResource {

    protected static final Logger LOGGER = Logger.getLogger(DriversResource.class.getName());

    @Autowired
    protected DriverService driverService;

    @RequestMapping(method = RequestMethod.POST)
    public Response createDriver(@RequestBody Driver driver) {
        Driver createDriver = driverService.createDriver(driver);
        return Response.successful(createDriver);
    }

    @RequestMapping
    public Response getDrivers() {
        return Response.successful(driverService.getDrivers());
    }

    @RequestMapping("/available")
    public Response getAvailableDriversInfo() {
        AvailableDriversRequest availableDriversRequest = new AvailableDriversRequest();
        availableDriversRequest.setCoordinate(new Coordinate(0, 0));
        availableDriversRequest.setRatio(0);
        return Response.successful(availableDriversRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/available")
    public Response getAvailableDrivers(@RequestBody AvailableDriversRequest availableDriversRequest) {
        return Response.successful(driverService.getAvailableDrivers(availableDriversRequest));
    }

}
