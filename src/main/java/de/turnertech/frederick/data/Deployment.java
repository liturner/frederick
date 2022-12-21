package de.turnertech.frederick.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deployment")
public class Deployment implements Serializable {
    
    private String name;

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
    
}
