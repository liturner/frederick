package de.turnertech.frederick.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.turnertech.frederick.services.Logging;

public class LoggingTests {

    @Test
    @DisplayName("Check logger exists ")
	public void loggerExists() {
        assertNotNull(Logging.LOGGER);
    }

}
