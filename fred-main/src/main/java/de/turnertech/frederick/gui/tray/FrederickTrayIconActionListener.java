package de.turnertech.frederick.gui.tray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.turnertech.frederick.main.Application;

public class FrederickTrayIconActionListener implements ActionListener {

    public static final String EXIT_COMMAND = "EXIT";

    public static final String SHOW_ETB_COMMAND = "SHOW_ETB";

    public static final String SHOW_MAP_COMMAND = "SHOW_MAP";

    public static final String SHOW_DEPLOYMENT_MANAGER_COMMAND = "SHOW_EINSATZ_MANAGER";

    @Override
    public void actionPerformed(ActionEvent e) {

        if(EXIT_COMMAND.equals(e.getActionCommand())) {
            Application.exit();  
        } else if (SHOW_ETB_COMMAND.equals(e.getActionCommand())) {
            Application.getEtbFrame().setVisible(true);
        } else if (SHOW_DEPLOYMENT_MANAGER_COMMAND.equals(e.getActionCommand())) {
            Application.getDeploymentFrame().setVisible(true);
        } else if (SHOW_MAP_COMMAND.equals(e.getActionCommand())) {
            Application.getMapFrame().setVisible(true);
        }
    }
}
