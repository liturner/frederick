package de.turnertech.frederick.gui.deployment;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DeploymentTablePane extends JScrollPane {
    
    private final DeploymentTable table;

    public DeploymentTablePane() {
        table = new DeploymentTable();
        this.setViewportView(table);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setMinimumSize(new Dimension(200,200));
    }

    public DeploymentTable getTable() {
        return this.table;
    }

}
