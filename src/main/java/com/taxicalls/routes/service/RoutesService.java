package com.taxicalls.routes.service;

import com.taxicalls.routes.model.Route;
import com.taxicalls.routes.model.RoutesRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author romulo
 */
@Service
public class RoutesService {

    @Autowired
    protected RoutesRepository routesRepository;

    public List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();
        Iterable<Route> findAll = routesRepository.findAll();
        for (Route route : findAll) {
            routes.add(route);
        }
        return routes;
    }

}
