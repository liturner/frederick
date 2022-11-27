package turnertech.frederick;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.util.logging.Level;

import turnertech.frederick.gui.etb.FrederickMainFrame;
import turnertech.frederick.gui.tray.FrederickTrayIcon;

/**
 * Currently the only executable method for this application. Used to analyse
 * all balls with all methods.
 */
public class Application {

    private static FrederickMainFrame frame = null;

    /**
     * Basic no gui analysis using all methods and printing results using the loggers.
     * 
     * @param args System provided arguments
     */
    public static void main(String[] args) {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            Logging.LOGGER.log(Level.SEVERE, "SystemTray is not supported");
            return;
        }
        final FrederickTrayIcon trayIcon = new FrederickTrayIcon();
        final SystemTray tray = SystemTray.getSystemTray();
              
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            Logging.LOGGER.log(Level.SEVERE, "SystemTray is not supported");
            return;
        }

        frame = new FrederickMainFrame();        
        frame.setVisible(true);
    }

    public static void showETB() {
        frame.setVisible(true);
    }

    public static void exit() {
        System.exit(0);
    }
}