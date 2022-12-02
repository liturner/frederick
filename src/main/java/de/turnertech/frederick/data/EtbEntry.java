package de.turnertech.frederick.data;

import java.io.Serializable;
import java.time.Instant;

public class EtbEntry implements Serializable {
    
    private final Instant timestamp;

    private final String user;

    private final String entry;

    private String notes;

    public EtbEntry(final Instant timestamp, String user, final String entry) {
        this.timestamp = timestamp;
        this.user = user;
        this.entry = entry;
        this.notes = "";
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
