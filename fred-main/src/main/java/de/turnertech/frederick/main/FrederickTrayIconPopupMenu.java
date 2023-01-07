package de.turnertech.frederick.main;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.util.List;

import de.turnertech.frederick.services.FrameProvider;


public class FrederickTrayIconPopupMenu extends PopupMenu {

    public FrederickTrayIconPopupMenu() {
        super();

        MenuItem menuItem = new MenuItem("Map");
        menuItem.addActionListener(new FrederickTrayIconActionListener());
        menuItem.setActionCommand(FrederickTrayIconActionListener.SHOW_MAP_COMMAND);
        this.add(menuItem);

        List<FrameProvider> frameProviders = FrameProvider.getInstances();
        for(FrameProvider frameProvider : frameProviders) {
            menuItem = new MenuItem(frameProvider.getFrameName());
            menuItem.addActionListener(new FrederickTrayIconActionListener());
            menuItem.setActionCommand(frameProvider.getShowFrameActionCommand());
            this.add(menuItem);
        }

        

        this.addSeparator();
        
        menuItem = new MenuItem("Beenden");
        menuItem.addActionListener(new FrederickTrayIconActionListener());
        menuItem.setActionCommand(FrederickTrayIconActionListener.EXIT_COMMAND);
        this.add(menuItem);
    }
    
}
