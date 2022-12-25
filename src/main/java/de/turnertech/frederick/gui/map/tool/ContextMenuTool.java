package de.turnertech.frederick.gui.map.tool;

import javax.swing.JPopupMenu;

import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

import de.turnertech.frederick.gui.map.action.SetBullseyeAction;

public class ContextMenuTool extends CursorTool {
    
    public ContextMenuTool(final JMapPane mapPane) {
        this.setMapPane(mapPane);
    }

    @Override
    public void onMouseReleased(MapMouseEvent mapMouseEvent) {
        if(mapMouseEvent.getButton() == MapMouseEvent.BUTTON3) {
            JPopupMenu contextMentu = new JPopupMenu();
            contextMentu.add(new SetBullseyeAction(getMapPane(), mapMouseEvent.getX(), mapMouseEvent.getY()));
            contextMentu.show(mapMouseEvent.getComponent(), mapMouseEvent.getX(), mapMouseEvent.getY());
            mapMouseEvent.consume();
        }
    }

}
