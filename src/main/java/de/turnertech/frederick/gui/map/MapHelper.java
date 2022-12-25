package de.turnertech.frederick.gui.map;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.measure.AngleFormat;

public class MapHelper {
    
    public static final AngleFormat ANGLE_FORMAT = new AngleFormat("DD°MM'SS\"");

    public static final String format(DirectPosition2D position) {
        return String.format("E%s N%s", MapHelper.ANGLE_FORMAT.format(position.getX()), MapHelper.ANGLE_FORMAT.format(position.getY()));
    }

    private MapHelper() {

    }

}
