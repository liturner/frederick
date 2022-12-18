package de.turnertech.frederick.gui.deployments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.Resources;

public class DeploymentToolBar extends JToolBar implements ActionListener {

    public static final String DELETE_COMMAND = "DEPLOYMENT_DELETE";

    public static final String END_COMMAND = "DEPLOYMENT_END";

    public static final String EXPORT_COMMAND = "DEPLOYMENT_EXPORT";

    public static final String PRINT_COMMAND = "DEPLOYMENT_PRINT";

    private final DeploymentTable deploymentTable;

    public DeploymentToolBar(DeploymentTable deploymentTable) {

        this.deploymentTable = deploymentTable;
        this.setFloatable(false);

        JButton menuItem = new JButton();
        menuItem.setIcon(Resources.getStop24pxIcon());
        menuItem.setToolTipText("Einsatz Beenden");
        menuItem.setActionCommand(END_COMMAND);
        menuItem.addActionListener(this);
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(Resources.getDelete24pxIcon());
        menuItem.setToolTipText("Einsatz Löschen");
        menuItem.setActionCommand(DELETE_COMMAND);
        menuItem.addActionListener(this);
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(Resources.getExport24pxIcon());
        menuItem.setToolTipText("Einsatz Exportieren");
        menuItem.setActionCommand(EXPORT_COMMAND);
        menuItem.addActionListener(this);
        this.add(menuItem);
        
        menuItem = new JButton();
        menuItem.setIcon(Resources.getPrint24pxIcon());
        menuItem.setToolTipText("Einsatz Drücken");
        menuItem.setActionCommand(PRINT_COMMAND);
        menuItem.addActionListener(this);
        this.add(menuItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(DELETE_COMMAND.equals(e.getActionCommand())) {
            deploymentTable.getSelectedRow();
        } else if (END_COMMAND.equals(e.getActionCommand())) {
            Application.getDatabase().closeDeployment("SomeText");
        } else if (EXPORT_COMMAND.equals(e.getActionCommand())) {
            
        } else if (PRINT_COMMAND.equals(e.getActionCommand())) {
            
        }
    }

}
