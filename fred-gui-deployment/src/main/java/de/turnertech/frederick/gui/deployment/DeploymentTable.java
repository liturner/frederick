package de.turnertech.frederick.gui.deployment;

import javax.swing.JTable;

public class DeploymentTable extends JTable {

    public DeploymentTable() {
        super();
        DeploymentTableModel tableModel = new DeploymentTableModel();
        this.setModel(tableModel);
        this.setColumnModel(tableModel.getColumnModel());
        this.setSelectionModel(new DeploymentTableSelectionModel());
    }
    
}
