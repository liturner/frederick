package de.turnertech.frederick.gui.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;


public class FrederickTrayIconPopupMenu extends PopupMenu {

    public FrederickTrayIconPopupMenu() {
        super();

        MenuItem menuItem = new MenuItem("Einsatz Manager");
        menuItem.addActionListener(new FrederickTrayIconActionListener());
        menuItem.setActionCommand(FrederickTrayIconActionListener.SHOW_DEPLOYMENT_MANAGER_COMMAND);
        this.add(menuItem);

        menuItem = new MenuItem("ETB");
        menuItem.addActionListener(new FrederickTrayIconActionListener());
        menuItem.setActionCommand(FrederickTrayIconActionListener.SHOW_ETB_COMMAND);
        this.add(menuItem);

        this.addSeparator();
        
        menuItem = new MenuItem("Beenden");
        menuItem.addActionListener(new FrederickTrayIconActionListener());
        menuItem.setActionCommand(FrederickTrayIconActionListener.EXIT_COMMAND);
        this.add(menuItem);
    }
    
}
