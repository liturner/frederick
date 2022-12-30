package de.turnertech.frederick.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Simple container class for our Logging activities. The logging config is part
 * of /de/turnertech/frederick/frederick.properties.
 */
public class Logging {
    
    public static final Logger LOGGER = Logger.getLogger("turnertech.frederick");

    private Logging() {

    }

    public static void initialise() {
        String storeRoot = System.getProperty("de.turnertech.frederick.store.location");
        String logFolder = System.getProperty("de.turnertech.frederick.store.log.folder", "Logs");

        Path fullLogFolder = Paths.get(storeRoot, logFolder);

        try {
            Files.createDirectories(fullLogFolder);
        } catch (Exception e) {
            Logging.LOGGER.log(Level.SEVERE, "Could not create database. Exiting!", e);
            Application.exit();
        }

        LogManager.getLogManager().reset();
        try {
            LogManager.getLogManager().readConfiguration(Logging.class.getResourceAsStream("logging.properties"));
        } catch (SecurityException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
