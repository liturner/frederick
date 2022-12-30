package de.turnertech.frederick.gui.deployments;

import javax.swing.DefaultListSelectionModel;

public class DeploymentTableSelectionModel extends DefaultListSelectionModel {
    
    public DeploymentTableSelectionModel() {
        this.setSelectionMode(SINGLE_SELECTION);
    }
    
}
