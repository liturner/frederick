package de.turnertech.frederick.gui.deployments;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.turnertech.frederick.Resources;

public class DeploymentToolBar extends JToolBar {

    public DeploymentToolBar() {

        JButton menuItem = new JButton();
        menuItem.setIcon(Resources.getStop24pxIcon());
        menuItem.setToolTipText("Einsatz Beenden");
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(Resources.getDelete24pxIcon());
        menuItem.setToolTipText("Einsatz Löschen");
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(Resources.getExport24pxIcon());
        menuItem.setToolTipText("Einsatz Exportieren");
        this.add(menuItem);
        
        menuItem = new JButton();
        menuItem.setIcon(Resources.getPrint24pxIcon());
        menuItem.setToolTipText("Einsatz Drücken");
        this.add(menuItem);

    }

}
