package de.turnertech.frederick.gui.map.action;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.MapPane;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import de.turnertech.frederick.gui.map.MapHelper;
import de.turnertech.frederick.main.Application;
import de.turnertech.frederick.main.Logging;

public class SetBullseyeAction extends AbstractAction {

    final transient MapPane mapPane;
    
    final transient Point2D screenPoint;

    public SetBullseyeAction(final MapPane mapPane, int x, int y) {
        super("Bullseye Setzen");
        this.mapPane = mapPane;
        screenPoint = new Point2D.Double(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point2D worldPoint = new Point2D.Double();
        mapPane.getScreenToWorldTransform().transform(screenPoint, worldPoint);
        DirectPosition2D worldPosition = new DirectPosition2D(mapPane.getDisplayArea().getCoordinateReferenceSystem(), worldPoint.getX(), worldPoint.getY());
        DirectPosition2D crs84Position;
        MathTransform transform;

        try {
            transform = CRS.findMathTransform(worldPosition.getCoordinateReferenceSystem(), DefaultGeographicCRS.WGS84, true);
            crs84Position = new DirectPosition2D(DefaultGeographicCRS.WGS84);
            transform.transform(worldPosition, crs84Position);
        } catch (FactoryException e2) {
            Logging.LOGGER.severe("Could not create CRS transform. Coordinates may be false!");
            return;
        } catch (MismatchedDimensionException | TransformException e1) {
            Logging.LOGGER.severe("Could not transform. Coordinates may be false!");
            return;
        }

        de.turnertech.frederick.data.Bullseye dataToStore = new de.turnertech.frederick.data.Bullseye(crs84Position.getX(), crs84Position.getY());
        Application.getService().setBullseye(dataToStore, MapHelper.format(crs84Position));
    }    
}
