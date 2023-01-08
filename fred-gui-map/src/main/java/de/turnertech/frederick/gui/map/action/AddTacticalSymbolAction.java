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

import de.turnertech.frederick.data.TacticalElement;
import de.turnertech.frederick.gui.map.MapHelper;
import de.turnertech.frederick.services.ApplicationService;
import de.turnertech.frederick.services.Logging;
import de.turnertech.tz.swing.TacticalSymbol;

public class AddTacticalSymbolAction extends AbstractAction {

    final transient MapPane mapPane;
    
    final transient Point2D screenPoint;

    final TacticalSymbol tacticalSymbol;

    public AddTacticalSymbolAction(final MapPane mapPane, double x, double y, TacticalSymbol tacticalSymbol) {
        super("Add Tactical Symbol");
        this.mapPane = mapPane;
        this.screenPoint = new Point2D.Double(x, y);
        this.tacticalSymbol = tacticalSymbol;
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

        TacticalElement dataToStore = new TacticalElement(crs84Position.getX(), crs84Position.getY());
        dataToStore.setIcon(tacticalSymbol.hashCode());
        
        ApplicationService.getInstance().addTacticalElement(dataToStore, MapHelper.format(crs84Position));
    }

}
