package de.turnertech.frederick.gui.map.tool;

 import java.awt.Cursor;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.AbstractZoomTool;

/**
 * A scroll tool which zooms based on the cursor position.
 */
public class ScrollTool extends AbstractZoomTool {

    public ScrollTool(final JMapPane mapPane) {
        this.setMapPane(mapPane);
    }

    @Override
    public Cursor getCursor() {
        return null;
    }

    @Override
    public void onMouseWheelMoved(MapMouseEvent mapMouseEvent) {
        final Rectangle paneArea = ((JComponent) getMapPane()).getVisibleRect();
        final DirectPosition2D mouseWorldPosition = mapMouseEvent.getWorldPos();
        final int scrolls = mapMouseEvent.getWheelAmount();
        final double scaleWorldBy = (scrolls > 0) ? scrolls * getZoom() : -1.0 / (scrolls * getZoom());
        final DirectPosition2D screenCenter = new DirectPosition2D(paneArea.getCenterX(), paneArea.getCenterY());

        DirectPosition2D screenCenterInWorldSpace  = new DirectPosition2D();
        getMapPane().getScreenToWorldTransform().transform(screenCenter, screenCenterInWorldSpace);
        
        // Calculate the new map center by first calculating the transform from mouse position to screen center, and then
        // scale that transform.
        final DirectPosition2D mapCenterPostXformWorld = new DirectPosition2D(
            mouseWorldPosition.getX() + (screenCenterInWorldSpace.getX() - mouseWorldPosition.getX()) * scaleWorldBy,
            mouseWorldPosition.getY() + (screenCenterInWorldSpace.getY() - mouseWorldPosition.getY()) * scaleWorldBy
        );

        final DirectPosition2D corner = new DirectPosition2D(
            mapCenterPostXformWorld.getX() - (getMapPane().getDisplayArea().getWidth() * 0.5 * scaleWorldBy),
            mapCenterPostXformWorld.getY() + (getMapPane().getDisplayArea().getHeight() * 0.5 * scaleWorldBy)
        );

        Envelope2D newMapArea = new Envelope2D();
        newMapArea.setFrameFromCenter(mapCenterPostXformWorld.getX(), mapCenterPostXformWorld.getY(), corner.getX(), corner.getY());
        getMapPane().setDisplayArea(newMapArea);
    }
}
