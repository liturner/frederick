package turnertech.frederick.data;

import java.time.Instant;

public class EtbEntry {
    
    private final Instant timestamp;

    private final String user;

    private final String entry;

    public EtbEntry(final Instant timestamp, String user, final String entry) {
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
