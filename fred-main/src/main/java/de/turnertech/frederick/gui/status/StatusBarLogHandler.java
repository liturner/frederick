package de.turnertech.frederick.gui.status;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A basic log handler to connect a callback mechanism to the status
 * bar.
 */
public class StatusBarLogHandler extends Handler {
    
    private final StatusBar statusBar;

    public StatusBarLogHandler(final StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    @Override
    public void publish(LogRecord logRecord) {
        statusBar.onLogRecieved(logRecord);
    }

    @Override
    public void flush() {
        // We do not have a buffer
    }

    @Override
    public void close() throws SecurityException {
        // We have no associated resources
    }

}
