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

import de.turnertech.frederick.services.ActionService;
import de.turnertech.frederick.services.ApplicationService;
import de.turnertech.frederick.services.FrameProvider;
import de.turnertech.frederick.services.Logging;
import de.turnertech.frederick.services.PersistanceProvider;
import de.turnertech.frederick.services.event.DeploymentOpenedEvent;

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

    /**
     * Basic no gui analysis using all methods and printing results using the loggers.
     * 
     * @param args System provided arguments
     */
    public static void main(String[] args) {
        Logging.initialise();
        Printing.initialise();

        PersistanceProvider persistanceProvider = PersistanceProvider.getInstance();
        Logging.LOGGER.info("PersistanceProvider: " + persistanceProvider.getClass().getName());

        ApplicationService applicationService = ApplicationService.getInstance();
        Logging.LOGGER.info("ApplicationService: " + applicationService.getClass().getName());

        
        List<FrameProvider> frameProviders = FrameProvider.getInstances();
        for (FrameProvider frameProvider : frameProviders) {
            Logging.LOGGER.info("FrameProvider: " + frameProvider.getClass().getName());
            Logging.LOGGER.info("FrameProviderFrame: " + frameProvider.getFrame().getClass().getName()); // This is important to trigger creation of the actuall Frames.
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

        // Technically, there is always a depolyment open. This is more a trigger to say "initialisation finished"
        ActionService.notifyActionListeners(new DeploymentOpenedEvent(tray));

        SwingUtilities.invokeLater(() -> {
            for (FrameProvider frameProvider : frameProviders) {
                frameProvider.getFrame().setVisible(true);
            }
        });
    }

    /**
     * Shows the system web browser with the manual page for a given window.
     * For example, if requested for ... class, then the
     * web browser should open to the manual page describing the deployment 
     * manager.
     * 
     * @param clazz Class to show help for.
     */
    public static void getHelp(Class<?> clazz) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("www.example.com");

            if(true) {
                oURL = new URI("www.example.com/example");
            } 

            desktop.browse(oURL);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}