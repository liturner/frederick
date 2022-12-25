package de.turnertech.frederick.gui.map.tool;

import java.awt.Point;

import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

/**
 * Basic pan tool with no icons etc. Intended as a basic mouse listener.
 */
public class PanTool extends CursorTool {
    
    private Point mousePosition;
    
    private boolean panning;

    public PanTool(final JMapPane mapPane) {
        this.setMapPane(mapPane);
        panning = false;
    }

    @Override
    public void onMousePressed(MapMouseEvent mapMouseEvent) {
        if(mapMouseEvent.getButton() == MapMouseEvent.BUTTON1) {
            mousePosition = mapMouseEvent.getPoint();
            panning = true;
        }
    }

    @Override
    public void onMouseDragged(MapMouseEvent mapMouseEvent) {
        if (panning) {
            Point newMousePosition = mapMouseEvent.getPoint();
            if (!newMousePosition.equals(mousePosition)) {
                getMapPane().moveImage(newMousePosition.x - mousePosition.x, newMousePosition.y - mousePosition.y);
                mousePosition = newMousePosition;
                mapMouseEvent.consume();
            }
        }
    }

    @Override
    public void onMouseReleased(MapMouseEvent mapMouseEvent) {
        panning = false;
    }

    @Override
    public boolean drawDragBox() {
        return false;
    }
}
