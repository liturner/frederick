package de.turnertech.frederick.gui.map.action;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.swing.MapPane;

import de.turnertech.frederick.gui.map.feature.Bullseye;

public class SetBullseyeAction extends AbstractAction {

    final MapPane mapPane;
    
    final Point2D screenPoint;

    public SetBullseyeAction(final MapPane mapPane, int x, int y) {
        super("Bullseye Setzen");
        this.mapPane = mapPane;
        screenPoint = new Point2D.Double(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point2D worldPoint = new Point2D.Double();
        mapPane.getScreenToWorldTransform().transform(screenPoint, worldPoint);
        Bullseye.set(new DirectPosition2D(mapPane.getDisplayArea().getCoordinateReferenceSystem(), worldPoint.getX(), worldPoint.getY()));
    }
    
}
