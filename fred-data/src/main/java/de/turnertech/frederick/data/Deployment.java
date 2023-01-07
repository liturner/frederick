package de.turnertech.frederick.data;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Data class for serialising the deployments. This class should never have complex
 * functionality, rather simple getters and setters for parameters.
 */
@XmlRootElement(name = "deployment")
public class Deployment {
    
    private String name;

    private Bullseye bullseye = null;

    @XmlElement(name = "etbEntry")
    private LinkedList<EtbEntry> etbEntries;

    @XmlElement(name = "tacticalSymbolEntry")
    private LinkedList<TacticalElement> tacticalSymbolEntries;

    public Deployment() {
        etbEntries = new LinkedList<>();
        tacticalSymbolEntries = new LinkedList<>();
        name = "Test";
    }

    public List<EtbEntry> getEtbEntries() {
        return etbEntries;
    }

    public List<TacticalElement> getTacticalSymbolEntries() {
        return tacticalSymbolEntries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the single point of interest related to this Deployment. Most likely the Meldekopf
     * 
     * @return The Deployment focus point
     */
    public Bullseye getBullseye() {
        return bullseye;
    }

    public void setBullseye(Bullseye bullseye) {
        this.bullseye = bullseye;
    }

}
