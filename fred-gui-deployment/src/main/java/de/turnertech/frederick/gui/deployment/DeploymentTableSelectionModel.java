package de.turnertech.frederick.gui.deployment;

import javax.swing.DefaultListSelectionModel;

public class DeploymentTableSelectionModel extends DefaultListSelectionModel {
    
    public DeploymentTableSelectionModel() {
        this.setSelectionMode(SINGLE_SELECTION);
    }
    
}
