package com.taxicalls.routes.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface RoutesRepository extends Repository<Route, Integer> {

    public Route save(Route route);

    public Iterable<Route> findAll();

    public Route findOne(Integer id);

    @Query("SELECT count(*) from Route")
    public int countRoutes();

}
