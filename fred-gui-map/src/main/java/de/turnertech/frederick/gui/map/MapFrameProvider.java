package de.turnertech.frederick.gui.map;

import javax.swing.JFrame;

import de.turnertech.frederick.services.FrameProvider;

public class MapFrameProvider implements FrameProvider {
    
    private static JFrame instance;

    @Override
    public JFrame getFrame() {
        if(instance == null) {
            instance = new MapFrame();
        }
        return instance;
    }

    @Override
    public String getShowFrameActionCommand() {
        return "SHOW_MAP_ACTION_COMMAND";
    }

    @Override
    public String getFrameName() {
        return "Einsatzkarte";
    }

}
