package de.turnertech.frederick.data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A secure entry. Once created most of it is intentionally immutable. This aims to ensure
 * that, once written, ETB entries cannot be modified. This is one of the legal concerns
 * which many have when handling the ETB, in that in the case of an accident, a digital
 * ETB may be easily forged.
 */
public class EtbEntry implements Serializable {
    
    /**
     * The time at which the entry was made. Not the class instance, rather the user entry.
     */
    private final Instant timestamp;

    /**
     * The user who wrote the entry.
     */
    private final String user;

    /**
     * The ETB entry itself. This is just plain text, may be multi line, may be empty etc.
     */
    private final String entry;

    private String notes;

    public EtbEntry(final Instant timestamp, final String user, final String entry) {
        this(timestamp, user, entry, "");
    }

    public EtbEntry(final Instant timestamp, final String user, final String entry, String notes) {
        if(timestamp == null) {
            throw new IllegalArgumentException("EtbEntry may not be created with a null timestamp.");
        }
        if(user == null || "".equals(user)) {
            throw new IllegalArgumentException("EtbEntry may not be created with an empty user.");
        }
        
        this.timestamp = timestamp;
        this.user = user;
        this.entry = entry;
        this.notes = notes;
    }

    /**
     * Returns the time at which the ETB entry was origionally written. This value
     * is set in the constructor, is final and is intended to be immutable.
     * 
     * @return The time at which the ETB entry was origionally written, never null.
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the user who wrote the ETB entry. This value
     * is set in the constructor, is final and is intended to be immutable.
     * 
     * @return The user who wrote the ETB entry, never null or empty.
     */
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
