package turnertech.frederick;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResourcesTests {

    @Test
    @DisplayName("THW Icon Exists")
	public void thwIconExists() {
        assertNotNull(Resources.getThwIconUrl());
    }

}
