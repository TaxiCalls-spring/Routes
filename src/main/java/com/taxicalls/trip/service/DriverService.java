package com.taxicalls.trip.service;

import com.taxicalls.trip.model.Coordinate;
import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.model.Status;
import com.taxicalls.trip.model.Trip;
import com.taxicalls.trip.repository.DriverRepository;
import com.taxicalls.trip.repository.TripRepository;
import com.taxicalls.trip.resources.AvailableDriversRequest;
import com.taxicalls.trip.resources.ChooseDriverRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author romulo
 */
@Service
public class DriverService {

    private static final Logger LOGGER = Logger.getLogger(DriverService.class.getName());

    private final DriverRepository driverRepository;

    @Autowired
    private TripRepository tripRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public Driver getDriver(Long id) {
        return driverRepository.findOne(id);
    }

    public List<Driver> getAvailableDrivers(AvailableDriversRequest availableDriversRequest) {
        Coordinate coordinate = availableDriversRequest.getCoordinate();
        int ratio = availableDriversRequest.getRatio();
        if (coordinate == null) {
            return null;
        }
        if (coordinate.getLatitude() == null) {
            return null;
        }
        if (coordinate.getLongitude() == null) {
            return null;
        }
        Iterable<Driver> drivers = driverRepository.findAll();
        Iterable<Trip> trips = tripRepository.findAll();
        List<Driver> busyDrivers = new ArrayList<>();
        trips.forEach((trip) -> {
            busyDrivers.add(trip.getDriver());
        });
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver.getAtualCoordinate() == null) {
                continue;
            }
            if (driver.getAtualCoordinate().getLatitude() == null) {
                continue;
            }
            if (driver.getAtualCoordinate().getLongitude() == null) {
                continue;
            }
            if (driver.getStatus().equals(Status.NOT_WORKING)) {
                continue;
            }
            if (driver.getAtualCoordinate().getEuclidienDistance(coordinate) <= ratio) {
                availableDrivers.add(driver);
            }
        }
        availableDrivers.removeAll(busyDrivers);
        return availableDrivers;
    }

    @Transactional
    public boolean chooseDriver(ChooseDriverRequest chooseDriverRequest) {
        Iterable<Trip> findAll = tripRepository.findAll();
        for (Trip trip : findAll) {
            if (trip.getAuthor() == null) {
                continue;
            }
            if (trip.getAuthor().equals(chooseDriverRequest.getPassenger())) {
                trip.setDriver(chooseDriverRequest.getDriver());
                entityManager.merge(trip);
                return true;
            }
        }
        return false;
    }

}
