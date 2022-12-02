package de.turnertech.frederick.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Deployment implements Serializable {
    
    private LinkedList<EtbEntry> etbEntries;

    public Deployment() {
        etbEntries = new LinkedList<>();
    }

    public List<EtbEntry> getEtbEntryList() {
        return etbEntries;
    }
    
}
