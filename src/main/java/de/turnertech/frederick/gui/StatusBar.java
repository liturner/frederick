package de.turnertech.frederick.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {
    
    public StatusBar() {
        super();

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel status = new JLabel();
        status.setHorizontalAlignment(SwingConstants.LEFT);
        status.setText("TOOL_TIP_TEXT_KEY");
        this.add(status);
    }

}
