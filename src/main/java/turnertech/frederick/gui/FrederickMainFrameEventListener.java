package turnertech.frederick.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrederickMainFrameEventListener extends WindowAdapter {
    
    private final FrederickMainFrame frederickMainFrame;

    public FrederickMainFrameEventListener(final FrederickMainFrame frederickMainFrame) {
        this.frederickMainFrame = frederickMainFrame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frederickMainFrame.windowClosing();
    }

}
