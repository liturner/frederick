package turnertech.frederick;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggingTests {

    @Test
    @DisplayName("Check logger exists ")
	public void loggerExists() {
        assertNotNull(Logging.LOGGER);
    }

}
