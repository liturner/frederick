package de.turnertech.frederick.data;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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

    public Deployment() {
        etbEntries = new LinkedList<>();
        name = "Test";
    }

    public List<EtbEntry> getEtbEntries() {
        return etbEntries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Bullseye getBullseye() {
        return bullseye;
    }

    public void setBullseye(Bullseye bullseye) {
        this.bullseye = bullseye;
    }

}
