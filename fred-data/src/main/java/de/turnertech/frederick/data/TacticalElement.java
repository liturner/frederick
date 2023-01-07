package de.turnertech.frederick.data;

import java.io.Serializable;

/**
 * A Tactical Element is something which is represented on a Map. It could be considered an instance of a Tactical
 * Symbol, but rather a Tactical Element would be represented by a Tactical Symbol.
 */
public class TacticalElement implements Serializable {
    
    // TODO: Get a unique TSil ID

    /** Longitude */
    private double x;

    /** Latitude */
    private double y;

    /**
     * Default constructor initialising to [0 0].
     */
    public TacticalElement() {
        this(0.0, 0.0);        
    }

    /**
     * Overridden constructor taking x and y as long and lat respectively.
     * 
     * @param x Longitude in decimal degrees.
     * @param y Latitude in decimal degrees.
     */
    public TacticalElement(final double x, final double y) {
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
