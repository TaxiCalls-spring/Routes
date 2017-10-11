/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.routes.model;

import java.io.Serializable;

/**
 *
 * @author romulo
 */
public class Coordinate implements Serializable, Comparable<Coordinate> {

    private final long longitude;
    private final long latitude;

    protected Coordinate() {
        this.latitude = 0;
        this.longitude = 0;
    }

    public Coordinate(long longitude, long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public double getEuclidienDistance(Coordinate coordinate) {
        return Math.sqrt(
                Math.pow(this.getLatitude() - coordinate.getLatitude(), 2)
                + Math.pow(this.getLongitude() - coordinate.getLongitude(), 2)
        );
    }

    @Override
    public int compareTo(Coordinate coordinate) {
        return 1;
    }

}
