package de.turnertech.frederick.gui.map;

import javax.swing.JToolBar;

import org.geotools.swing.MapPane;

import de.turnertech.frederick.gui.map.action.FocusBullseyeAction;

public class MapToolbar extends JToolBar  {
    
    private final FocusBullseyeAction focusBullseyeAction;

    public MapToolbar(final MapPane mapPane) {
        focusBullseyeAction = new FocusBullseyeAction(mapPane);
        focusBullseyeAction.setEnabled(false);
        this.add(focusBullseyeAction);
    }

    public void setFocusBullseyeActionEnabled(final boolean newValue) {
        focusBullseyeAction.setEnabled(newValue);
    }

}
