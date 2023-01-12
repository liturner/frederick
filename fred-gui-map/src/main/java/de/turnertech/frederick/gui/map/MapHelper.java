package de.turnertech.frederick.gui.map;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.measure.AngleFormat;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import de.turnertech.frederick.services.Logging;

public class MapHelper {
    
    public static final AngleFormat ANGLE_FORMAT = new AngleFormat("DD°MM'SS\"");

    public static final CoordinateReferenceSystem WGS_84 = DefaultGeographicCRS.WGS84;

    public static final CoordinateReferenceSystem UTMREF;

    public static final String format(DirectPosition2D position) {
        return String.format("E%s N%s", MapHelper.ANGLE_FORMAT.format(position.getX()), MapHelper.ANGLE_FORMAT.format(position.getY()));
    }

    static {
        CoordinateReferenceSystem crs = null;
        try {
            crs = CRS.decode("EPSG:32632");
        } catch (FactoryException e) {
            Logging.LOGGER.severe("Could not decode CRS EPSG:4326");
            System.exit(-1);
        }
        UTMREF = crs;
        if(UTMREF == null) {
            System.exit(-1);
        }
    }

    private MapHelper() {

    }

    public static DirectPosition2D getWgs84Position(DirectPosition2D position) {
        DirectPosition2D crs84Position;
        MathTransform transform;

        try {
            transform = CRS.findMathTransform(position.getCoordinateReferenceSystem(), WGS_84, true);
            crs84Position = new DirectPosition2D(WGS_84);
            transform.transform(position, crs84Position);
        } catch (FactoryException | MismatchedDimensionException | TransformException e1) {
            Logging.LOGGER.severe("Could not transform. Position is false!");
            System.exit(-1);
            return null;
        }
        
        return crs84Position;
    }

}
