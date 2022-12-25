package de.turnertech.frederick.gui.map;

import javax.swing.JToolBar;

import org.geotools.swing.MapPane;
import org.geotools.swing.action.NoToolAction;

import de.turnertech.frederick.gui.map.action.FocusBullseyeAction;

public class MapToolbar extends JToolBar  {
    
    public MapToolbar(MapPane mapPane) {
        this.add(new NoToolAction(mapPane));
        this.add(new FocusBullseyeAction(mapPane));
    }

}
