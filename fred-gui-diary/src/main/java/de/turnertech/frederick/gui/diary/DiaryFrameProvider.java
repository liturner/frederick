package de.turnertech.frederick.gui.diary;

import javax.swing.JFrame;

import de.turnertech.frederick.services.FrameProvider;

public class DiaryFrameProvider implements FrameProvider {
    
    private static JFrame instance;

    @Override
    public JFrame getFrame() {
        if(instance == null) {
            instance = new FrederickEtbFrame();
        }
        return instance;
    }

    @Override
    public String getShowFrameActionCommand() {
        return "SHOW_ETB_ACTION_COMMAND";
    }

    @Override
    public String getFrameName() {
        return "ETB";
    }

}
