package de.turnertech.frederick.data;

import java.io.Serializable;

public class TacticalElement implements Serializable {
    
    // TODO: Get a unique TSil ID

    private double x;

    private double y;

    public TacticalElement() {
        this(0.0, 0.0);        
    }

    public TacticalElement(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

}
