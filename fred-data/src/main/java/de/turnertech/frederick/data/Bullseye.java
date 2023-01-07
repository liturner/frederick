package de.turnertech.frederick.data;

import java.io.Serializable;

/**
 * Represents a point of interest. This class assumes WGS 84.
 */
public class Bullseye implements Serializable {
    
    /** Longitude */
    private double x;

    /** Latitude */
    private double y;

    /**
     * Default constructor initialising to [0 0].
     */
    public Bullseye() {
        this(0.0, 0.0);        
    }

    /**
     * Overridden constructor taking x and y as long and lat respectively.
     * 
     * @param x Longitude in decimal degrees.
     * @param y Latitude in decimal degrees.
     */
    public Bullseye(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the longitude in decimal degrees.
     * 
     * @return Longitude in decimal degrees.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the longitude in decimal degrees.
     * 
     * @param x Longitude in decimal degrees.
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Get the latitude in decimal degrees.
     * 
     * @return Latitude in decimal degrees.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the latitude in decimal degrees.
     * 
     * @param y Latitude in decimal degrees.
     */
    public void setY(final double y) {
        this.y = y;
    }

}
