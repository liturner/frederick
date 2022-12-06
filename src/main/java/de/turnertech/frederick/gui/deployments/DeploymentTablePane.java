package de.turnertech.frederick.gui.deployments;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DeploymentTablePane extends JScrollPane {
    
    public DeploymentTablePane() {
        super(new DeploymentTable());
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setMinimumSize(new Dimension(200,200));
    }

}
