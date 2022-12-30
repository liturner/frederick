package de.turnertech.frederick.data;

import java.io.Serializable;
import java.util.Date;

public class EtbEntry implements Serializable {
    
    /**
     * The time at which the entry was made.
     */
    private Date timestamp;

    /**
     * The user who wrote the entry.
     */
    private String user;

    /**
     * The ETB entry itself. This is just plain text, may be multi line, may be empty etc.
     */
    private String entry;

    private String notes;

    public EtbEntry() {

    }

    public EtbEntry(final Date timestamp, final String user, final String entry) {
        this(timestamp, user, entry, "");
    }

    public EtbEntry(final Date timestamp, final String user, final String entry, String notes) {
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
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public void setUser(String user) {
        this.user = user;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
