package turnertech.frederick.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;

public class FrederickMainFrame extends Frame {
    
    public FrederickMainFrame() {
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(500, 500);

        this.add(new Label("Hello!"));
        
    }

}
