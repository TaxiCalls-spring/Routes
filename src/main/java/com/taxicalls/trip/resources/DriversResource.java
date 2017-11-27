package com.taxicalls.trip.resources;

import com.taxicalls.protocol.Response;
import com.taxicalls.trip.model.Coordinate;
import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.service.DriverService;
import java.util.List;
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

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public Response updateDriver(@RequestBody Driver driver) {
        Driver stored = driverService.getDriver(driver.getId());
        if (stored == null) {
            return Response.notFound();
        }
        driverService.createDriver(driver);
        return Response.successful(driver);
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
        List<Driver> availableDrivers = driverService.getAvailableDrivers(availableDriversRequest);
        if (availableDrivers == null) {
            Response.error("latitude incomplete");
        }
        return Response.successful(availableDrivers);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/choose")
    public Response chooseDriver(@RequestBody ChooseDriverRequest chooseDriverRequest) {
        boolean chooseDriver = driverService.chooseDriver(chooseDriverRequest);
        if (chooseDriver) {
            return Response.successful();
        }
        return Response.error("driver or trip not found");
    }
}
