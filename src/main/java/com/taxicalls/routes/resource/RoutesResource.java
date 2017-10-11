package com.taxicalls.routes.resource;

import com.taxicalls.routes.model.Coordinate;
import com.taxicalls.routes.model.Route;
import com.taxicalls.routes.resource.exceptions.RouteNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taxicalls.routes.model.RoutesRepository;
import com.taxicalls.routes.service.RoutesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class RoutesResource {

    protected Logger logger = Logger.getLogger(RoutesResource.class.getName());
    protected RoutesRepository routesRepository;

    @Autowired
    protected RoutesService routesService;

    @Autowired
    public RoutesResource(RoutesRepository routesRepository) {
        this.routesRepository = routesRepository;
        logger.log(Level.INFO, "RoutesRepository says system has {0} routes", routesRepository.countRoutes());
    }

    @RequestMapping("/routes")
    public List<Route> getRoutes() {
        return routesService.getRoutes();
    }

    @RequestMapping("/routes/{id}")
    public Route getRoute(@PathVariable("id") Integer id) {
        logger.log(Level.INFO, "getRoute() invoked: {0}", id);
        Route route = routesRepository.findOne(id);
        logger.log(Level.INFO, "byId() found: {0}", route);
        if (route == null) {
            throw new RouteNotFoundException(id);
        } else {
            return route;
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/available")
    public List<Route> getAvailableRoutes(Coordinate coordinate) {
        Iterable<Route> routes = routesRepository.findAll();
        List<Route> availableRoutes = new ArrayList<>();
        for (Route route : routes) {
            if (route.getDistance(coordinate) < 5) {
                availableRoutes.add(route);
            }
        }
        return availableRoutes;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes")
    public Route createRoute(Route route) {
        Route save = routesRepository.save(route);
        return save;
    }
}
