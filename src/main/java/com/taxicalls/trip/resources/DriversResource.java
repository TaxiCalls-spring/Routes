package com.taxicalls.trip.resources;

import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.resources.exceptions.DriverNotFoundException;
import com.taxicalls.trip.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/drivers")
public class DriversResource {

    protected static final Logger LOGGER = Logger.getLogger(DriversResource.class.getName());

    @Autowired
    protected DriverService driverService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Driver createDriver(Driver driver) {
        return driverService.createDriver(driver);
    }

    @RequestMapping("/")
    public List<Driver> getDrivers() {
        return driverService.getDrivers();
    }

    @RequestMapping("/{id}")
    public Driver getDriver(@PathVariable("id") Integer id) {
        LOGGER.log(Level.INFO, "getDriver() invoked: {0}", id);
        Driver driver = driverService.getDriver(id);
        LOGGER.log(Level.INFO, "byId() found: {0}", driver);
        if (driver == null) {
            throw new DriverNotFoundException(id);
        } else {
            return driver;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/available")
    public List<Driver> getAvailableDrivers(AvailableDriversRequest availableDriversRequest) {
        return driverService.getAvailableDrivers(availableDriversRequest);
    }

}
