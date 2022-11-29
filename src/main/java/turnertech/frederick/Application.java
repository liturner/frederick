package turnertech.frederick;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.io.IOException;
import java.util.logging.Level;

import turnertech.frederick.data.Deployment;
import turnertech.frederick.gui.etb.FrederickEtbFrame;
import turnertech.frederick.gui.tray.FrederickTrayIcon;

/**
 * Currently the only executable method for this application. Used to analyse
 * all balls with all methods.
 */
public class Application {

    private static FrederickEtbFrame frame = null;

    private static Deployment currentDeployment = new Deployment();

    public static final String CURRENT_USER = System.getProperty("user.name");

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

        load();

        frame = new FrederickEtbFrame();        
        frame.setVisible(true);
    }

    public static void showETB() {
        frame.setVisible(true);
    }

    public static void exit() {
        System.exit(0);
    }

    public static void save() {
        currentDeployment.save();
    }

    public static void load() {
        try {
            currentDeployment = (Deployment)(Serialization.deserialize(System.getProperty("user.home") + "\\CurrentDeployment.thw"));
        } catch (ClassNotFoundException | IOException e) {
            Logging.LOGGER.log(Level.SEVERE, "Cannot load current deployment", e);
        }
        
    }

    public static Deployment getCurrentDeployment() {
        return currentDeployment;
    }
}