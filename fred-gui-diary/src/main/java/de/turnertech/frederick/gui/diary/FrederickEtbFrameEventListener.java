package de.turnertech.frederick.gui.diary;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrederickEtbFrameEventListener extends WindowAdapter {
    
    private final FrederickEtbFrame frederickMainFrame;

    public FrederickEtbFrameEventListener(final FrederickEtbFrame frederickMainFrame) {
        this.frederickMainFrame = frederickMainFrame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frederickMainFrame.windowClosing();
    }

}
