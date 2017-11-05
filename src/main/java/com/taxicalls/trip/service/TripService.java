package com.taxicalls.trip.service;

import com.taxicalls.trip.model.Trip;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taxicalls.trip.repository.TripRepository;
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

}
