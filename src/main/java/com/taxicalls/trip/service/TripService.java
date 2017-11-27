package com.taxicalls.trip.service;

import com.taxicalls.trip.model.Coordinate;
import com.taxicalls.trip.model.Driver;
import com.taxicalls.trip.model.Progress;
import com.taxicalls.trip.model.Status;
import com.taxicalls.trip.model.Trip;
import com.taxicalls.trip.repository.DriverRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taxicalls.trip.repository.TripRepository;
import com.taxicalls.trip.resources.DriverIsDistant;
import java.util.Collection;
import java.util.Collections;
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
public class TripService {

    protected static final Logger LOGGER = Logger.getLogger(TripService.class.getName());

    protected TripRepository tripRepository;

    @Autowired
    private DriverRepository driverRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
        LOGGER.log(Level.INFO, "TripRepository says system has {0} ", tripRepository.count());
    }

    @Transactional
    public Trip createTrip(Trip trip) {
        Trip merge = entityManager.merge(trip);
        return merge;
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        Iterable<Trip> findAll = tripRepository.findAll();
        for (Trip trip : findAll) {
            trips.add(trip);
        }
        return trips;
    }

    public Trip getTrip(Long id) {
        return tripRepository.findOne(id);
    }

    public boolean updateTrip(Trip trip) {
        Iterable<Trip> trips = tripRepository.findAll();
        for (Trip stored : trips) {
            if (stored.getDriver() == null) {
                continue;
            }
            if (stored.getDriver().equals(trip.getDriver())) {
                stored.setProgress(Progress.CONCLUDED);
                tripRepository.save(stored);
            }
        }
        return true;
    }

    @Transactional
    public Collection<DriverIsDistant> requestTrip(Trip trip) {
        Coordinate coordinate = trip.getAddressFrom().getCoordinate();
        trip.setProgress(Progress.REQUESTED);
        entityManager.merge(trip);
        Iterable<Trip> trips = tripRepository.findAll();
        Iterable<Driver> drivers = driverRepository.findAll();
        Collection<Driver> busyDrivers = new ArrayList<>();
        for (Trip stored : trips) {
            if (stored.getProgress() == null) {
                continue;
            }
            if (stored.getProgress().equals(Progress.IN_PROGRESS)) {
                continue;
            } else if (stored.getProgress().equals(Progress.MOVING_TO)) {
                continue;
            }
            busyDrivers.add(stored.getDriver());
        }
        Collection<Driver> availableDrivers = new ArrayList<>();
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
            availableDrivers.add(driver);
        }
        availableDrivers.removeAll(busyDrivers);
        List<DriverIsDistant> driverIsDistant = new ArrayList<>();
        availableDrivers.forEach((availableDriver) -> {
            driverIsDistant.add(new DriverIsDistant(availableDriver, coordinate));
        });
        Collections.sort(driverIsDistant);
        return driverIsDistant;
    }
}
