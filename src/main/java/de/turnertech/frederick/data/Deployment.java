package de.turnertech.frederick.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import de.turnertech.frederick.Logging;
import de.turnertech.frederick.Serialization;

public class Deployment implements Serializable {
    
    private LinkedList<EtbEntry> etbEntries;

    public Deployment() {
        etbEntries = new LinkedList<>();
    }

    public List<EtbEntry> getEtbEntryList() {
        return etbEntries;
    }

    public void save() {
        try {
            Serialization.serialize(this, System.getProperty("user.home") + "\\CurrentDeployment.thw");
        } catch (IOException e) {
            Logging.LOGGER.log(Level.SEVERE, "Unable to save the deployment!");
        }
    }
    
}
