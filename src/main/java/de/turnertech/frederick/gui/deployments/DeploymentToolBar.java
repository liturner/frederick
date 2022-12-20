package de.turnertech.frederick.gui.deployments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.Database;
import de.turnertech.frederick.Logging;
import de.turnertech.frederick.Resources;

public class DeploymentToolBar extends JToolBar implements ActionListener, ListSelectionListener {

    public static final String DELETE_COMMAND = "DEPLOYMENT_DELETE";

    public static final String END_COMMAND = "DEPLOYMENT_END";

    public static final String EXPORT_COMMAND = "DEPLOYMENT_EXPORT";

    public static final String PRINT_COMMAND = "DEPLOYMENT_PRINT";

    public static final String HELP_COMMAND = "HELP";

    private final DeploymentTable deploymentTable;

    JButton endDeploymentButton;

    JButton deleteDeploymentButton;

    JButton exportDeploymentButton;

    JButton printDeploymentButton;

    JButton helpButton;

    public DeploymentToolBar(DeploymentTable deploymentTable) {

        this.deploymentTable = deploymentTable;
        this.setFloatable(false);
        deploymentTable.getSelectionModel().addListSelectionListener(this);

        endDeploymentButton = new JButton();
        endDeploymentButton.setIcon(Resources.getStop24pxIcon());
        endDeploymentButton.setToolTipText("Einsatz Beenden");
        endDeploymentButton.setActionCommand(END_COMMAND);
        endDeploymentButton.addActionListener(this);
        this.add(endDeploymentButton);

        this.addSeparator();

        deleteDeploymentButton = new JButton();
        deleteDeploymentButton.setIcon(Resources.getDelete24pxIcon());
        deleteDeploymentButton.setToolTipText("Einsatz Löschen");
        deleteDeploymentButton.setActionCommand(DELETE_COMMAND);
        deleteDeploymentButton.addActionListener(this);
        deleteDeploymentButton.setEnabled(false);
        this.add(deleteDeploymentButton);

        exportDeploymentButton = new JButton();
        exportDeploymentButton.setIcon(Resources.getExport24pxIcon());
        exportDeploymentButton.setToolTipText("Einsatz Exportieren");
        exportDeploymentButton.setActionCommand(EXPORT_COMMAND);
        exportDeploymentButton.addActionListener(this);
        exportDeploymentButton.setEnabled(false);
        this.add(exportDeploymentButton);
        
        printDeploymentButton = new JButton();
        printDeploymentButton.setIcon(Resources.getPrint24pxIcon());
        printDeploymentButton.setToolTipText("Einsatz Drücken");
        printDeploymentButton.setActionCommand(PRINT_COMMAND);
        printDeploymentButton.addActionListener(this);
        printDeploymentButton.setEnabled(false);
        this.add(printDeploymentButton);

        this.add(Box.createHorizontalGlue());

        helpButton = new JButton();
        helpButton.setIcon(Resources.getHelp24pxIcon());
        helpButton.setToolTipText("Hilfe");
        helpButton.setActionCommand(HELP_COMMAND);
        helpButton.addActionListener(this);
        this.add(helpButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(DELETE_COMMAND.equals(e.getActionCommand())) {
            int selectedRow = deploymentTable.getSelectedRow();
            Object nameObject = deploymentTable.getModel().getValueAt(selectedRow, DeploymentTableModel.NAME);
            
            if(nameObject == null) {
                Logging.LOGGER.severe("Deployment had no name!");
                return;
            }

            String name = nameObject.toString();

            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete the deployment: " + name, "Einsatz Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION) {
                Application.getDatabase().deleteDeployment(name);
            }

        } 
        else if (END_COMMAND.equals(e.getActionCommand())) {
            String additionalInstructions = "";
            boolean nameValid = false;
            String name = "";
            while(!nameValid) {
                name = JOptionPane.showInputDialog(
                    this,
                    "Wie soll den Einsatz genannt?\n\n" + additionalInstructions,
                    "Einsatz Beenden",
                    JOptionPane.QUESTION_MESSAGE);

                if(name == null) {
                    Logging.LOGGER.info("User aborted the closing of the deployment.");
                    return;
                } else if(Application.getDatabase().isDeploymentExists(name)) {
                    additionalInstructions = "Name already exists! Choose another name.";
                } else if (Application.getDatabase().getPathToDeployment(name).isEmpty()) {
                    additionalInstructions = "Name invalid! Choose a valid file name.";
                } else {
                    nameValid = true;
                }                
            }

            Application.getDatabase().closeDeployment(name);
        } 
        else if (EXPORT_COMMAND.equals(e.getActionCommand())) {
            
        } 
        else if (PRINT_COMMAND.equals(e.getActionCommand())) {
            
        }
        else if (HELP_COMMAND.equals(e.getActionCommand())) {
            Application.getHelp(DeploymentFrame.class);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            final int selectedRow = deploymentTable.getSelectedRow();
            if(selectedRow > -1) {
                deleteDeploymentButton.setEnabled(true);
                exportDeploymentButton.setEnabled(true);
                printDeploymentButton.setEnabled(true);
                if (Database.CURRENT_DEPLOYMENT_FILE_NAME.equals(deploymentTable.getModel().getValueAt(selectedRow, DeploymentTableModel.NAME))) {
                    deleteDeploymentButton.setEnabled(false);
                }
            } 
            else {
                deleteDeploymentButton.setEnabled(false);
                exportDeploymentButton.setEnabled(false);
                printDeploymentButton.setEnabled(false);
            }
        }
    }
}
