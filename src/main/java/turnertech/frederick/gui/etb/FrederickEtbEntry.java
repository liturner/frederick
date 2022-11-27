package turnertech.frederick.gui.etb;

import java.time.Instant;

public class FrederickEtbEntry {
    
    private final Instant timestamp;

    private final String user;

    private final String entry;

    public FrederickEtbEntry(final Instant timestamp, String user, final String entry) {
        this.timestamp = timestamp;
        this.user = user;
        this.entry = entry;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getEntry() {
        return entry;
    }

}
