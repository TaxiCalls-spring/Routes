package com.taxicalls.trip.service;

import com.taxicalls.trip.model.Coordinate;
import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.repository.DriverRepository;
import com.taxicalls.trip.resources.AvailableDriversRequest;
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
public class DriverService {

    protected static final Logger LOGGER = Logger.getLogger(DriverService.class.getName());

    protected DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
        LOGGER.log(Level.INFO, "DriverRepository says system has {0} ", driverRepository.count());
    }

    public Driver createDriver(Driver driver) {
        Driver save = driverRepository.save(driver);
        return save;
    }

    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        Iterable<Driver> findAll = driverRepository.findAll();
        for (Driver driver : findAll) {
            drivers.add(driver);
        }
        return drivers;
    }

    public Driver getDriver(Integer id) {
        return driverRepository.findOne(id);
    }

    public List<Driver> getAvailableDrivers(AvailableDriversRequest availableDriversRequest) {
        Coordinate coordinate = availableDriversRequest.getCoordinate();
        int ratio = availableDriversRequest.getRatio();

        Iterable<Driver> drivers = driverRepository.findAll();
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver.getAtualCoordinate().getEuclidienDistance(coordinate) <= ratio) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

}