package de.turnertech.frederick;

import java.util.logging.Logger;

/**
 * Simple container class for our Logging activities. The logging config is part
 * of /de/turnertech/frederick/frederick.properties.
 */
public class Logging {
    
    public static final Logger LOGGER = Logger.getLogger("turnertech.frederick");

    private Logging() {

    }

}
