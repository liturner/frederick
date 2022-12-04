package de.turnertech.frederick.gui.deployments;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

public class DeploymentMenuBar extends JMenuBar {

    public DeploymentMenuBar() {

        JButton menuItem = new JButton();
        menuItem.setIcon(UIManager.getIcon("FileView.computerIcon"));
        menuItem.setToolTipText("Einsatz Beenden");
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(UIManager.getIcon("FileView.computerIcon"));
        menuItem.setToolTipText("Einsatz Löschen");
        this.add(menuItem);

        menuItem = new JButton();
        menuItem.setIcon(UIManager.getIcon("FileView.computerIcon"));
        menuItem.setToolTipText("Einsatz Exportieren");
        this.add(menuItem);
        
        menuItem = new JButton();
        menuItem.setIcon(UIManager.getIcon("FileView.computerIcon"));
        menuItem.setToolTipText("Einsatz Drücken");
        this.add(menuItem);
    }

}
