package de.turnertech.frederick.main;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.util.List;

import de.turnertech.frederick.services.FrameProvider;
import de.turnertech.frederick.services.PrintoutProvider;


public class FrederickTrayIconPopupMenu extends PopupMenu {

    public FrederickTrayIconPopupMenu() {
        super();

        Menu printMenu = new Menu("Print");

        List<PrintoutProvider> printoutProviders = PrintoutProvider.getInstances();
        for(PrintoutProvider printoutProvider : printoutProviders) {
            MenuItem menuItem = new MenuItem(printoutProvider.getPrintoutName());
            menuItem.addActionListener(new FrederickTrayIconPrintActionListener(printoutProvider));
            menuItem.setActionCommand(FrederickTrayIconPrintActionListener.PRINT_COMMAND);
            printMenu.add(menuItem);
        }

        this.add(printMenu);

        this.addSeparator();

        MenuItem menuItem;
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
