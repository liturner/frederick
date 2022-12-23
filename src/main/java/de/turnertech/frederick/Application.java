package de.turnertech.frederick;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;

import javax.swing.UIManager;

import de.turnertech.frederick.gui.deployments.DeploymentFrame;
import de.turnertech.frederick.gui.etb.FrederickEtbFrame;
import de.turnertech.frederick.gui.map.MapFrame;
import de.turnertech.frederick.gui.map.MapFrameTake2;
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
        
        try {
            UIManager.setLookAndFeel(System.getProperty("swing.defaultlaf", UIManager.getCrossPlatformLookAndFeelClassName()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static FrederickEtbFrame etbFrame = null;

    private static DeploymentFrame deploymentFrame = null;

    private static MapFrame mapFrame = null;

    private static final Database database = new Database();

    public static final String CURRENT_USER = System.getProperty("user.name");

    /**
     * Basic no gui analysis using all methods and printing results using the loggers.
     * 
     * @param args System provided arguments
     */
    public static void main(String[] args) {

        Logging.initialise();
        Printing.initialise();

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
        etbFrame = new FrederickEtbFrame();
        etbFrame.setVisible(true);
        mapFrame = new MapFrame();
        mapFrame.setVisible(true);
        MapFrameTake2 mapFrame2 = new MapFrameTake2();
        mapFrame2.setVisible(true);
    }

    public static FrederickEtbFrame getEtbFrame() {
        return etbFrame;
    }

    public static DeploymentFrame getDeploymentFrame() {
        return deploymentFrame;
    }

    public static MapFrame getMapFrame() {
        return mapFrame;
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

            if(FrederickEtbFrame.class.equals(clazz)) {
                oURL = new URI("www.example.com/example");
            } 

            desktop.browse(oURL);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}