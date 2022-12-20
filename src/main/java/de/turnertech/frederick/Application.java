package de.turnertech.frederick;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;

import de.turnertech.frederick.gui.deployments.DeploymentFrame;
import de.turnertech.frederick.gui.etb.FrederickEtbFrame;
import de.turnertech.frederick.gui.tray.FrederickTrayIcon;

/**
 * Currently the only executable method for this application. Used to analyse
 * all balls with all methods.
 */
public class Application {

    static {
        try (InputStream props = Application.class.getResourceAsStream("frederick.properties");) {
            System.getProperties().load(props);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static FrederickEtbFrame frame = null;

    private static DeploymentFrame deploymentFrame = null;

    private static final Database database = new Database();

    public static final String CURRENT_USER = System.getProperty("user.name");

    /**
     * Basic no gui analysis using all methods and printing results using the loggers.
     * 
     * @param args System provided arguments
     */
    public static void main(String[] args) {

        Logging.initialise();

        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            Logging.LOGGER.log(Level.SEVERE, "SystemTray is not supported");
            return;
        }
        
        final SystemTray tray = SystemTray.getSystemTray();
        final FrederickTrayIcon trayIcon = new FrederickTrayIcon();
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            Logging.LOGGER.log(Level.SEVERE, "SystemTray is not supported");
            return;
        }

        deploymentFrame = new DeploymentFrame();
        deploymentFrame.setVisible(true);
        frame = new FrederickEtbFrame();        
        frame.setVisible(true);
    }

    public static FrederickEtbFrame getEtbFrame() {
        return frame;
    }

    public static DeploymentFrame getDeploymentFrame() {
        return deploymentFrame;
    }

    public static void exit() {
        System.exit(0);
    }

    public static Database getDatabase() {
        return database;
    }

    /**
     * Shows the system web browser with the manual page for a given window.
     * For example, if requested for {@link DeploymentFrame} class, then the
     * web browser should open to the manual page describing the deployment 
     * manager.
     * 
     * @param clazz Class to show help for.
     */
    public static void getHelp(Class<?> clazz) {

        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("www.example.com");
            desktop.browse(oURL);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}