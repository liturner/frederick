package de.turnertech.frederick.gui.map;

import javax.swing.JToolBar;

import org.geotools.swing.MapPane;
import org.geotools.swing.action.NoToolAction;
import org.geotools.swing.action.PanAction;

public class MapToolbar extends JToolBar  {
    
    public MapToolbar(MapPane mapPane) {
        this.add(new NoToolAction(mapPane));
        this.add(new PanAction(mapPane));
    }

}
