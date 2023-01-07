package de.turnertech.frederick.gui.status;

import java.util.logging.LogRecord;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import de.turnertech.frederick.services.Logging;

public class StatusBar extends JPanel {
    
    private final JLabel status = new JLabel();

    public StatusBar() {
        super();

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        status.setHorizontalAlignment(SwingConstants.LEFT);
        status.setPreferredSize(new java.awt.Dimension(-1, 30));
        this.add(status);

        StatusBarLogHandler logHandler = new StatusBarLogHandler(this);
        Logging.LOGGER.addHandler(logHandler);
    }

    void onLogRecieved(LogRecord logRecord) {
        status.setText(logRecord.getMessage());
    }

}
