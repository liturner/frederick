package de.turnertech.frederick.main;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.turnertech.frederick.gui.deployments.DeploymentFrame;
import de.turnertech.frederick.gui.etb.FrederickEtbFrame;
import de.turnertech.frederick.gui.map.MapFrame;
import de.turnertech.frederick.gui.tray.FrederickTrayIcon;
import de.turnertech.frederick.services.FrameProvider;

/**
 * The core application class, hosting a few services which the sub modules can 
 * use to interact.
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

    private static Database database;

    private static Service service;

    public static final String CURRENT_USER = System.getProperty("user.name");

    /**
     * Basic no gui analysis using all methods and printing results using the loggers.
     * 
     * @param args System provided arguments
     */
    public static void main(String[] args) {

        Logging.initialise();
        Printing.initialise();
        database = new Database();
        service = new Service(database);

        List<FrameProvider> msgServices = FrameProvider.getInstances();
        for (FrameProvider msgService : msgServices) {
            Logging.LOGGER.info("Found a Service: " + msgService.getClass().getName());
            
        }

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
        mapFrame = new MapFrame();

        // Technically, there is always a depolyment open. This is more a trigger to say "initialisation finished"
        database.notifyActionListeners(Database.DEPLOYMENT_OPENED_EVENT_ID);

        SwingUtilities.invokeLater(() -> {
            etbFrame.setVisible(true);
            mapFrame.setVisible(true);
        });
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

    public static Service getService() {
        return service;
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