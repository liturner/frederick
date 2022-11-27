package turnertech.frederick.gui.tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;


public class FrederickTrayIconPopupMenu extends PopupMenu {

    public FrederickTrayIconPopupMenu() {
        super();

        MenuItem showItem = new MenuItem("Show ETB");
        showItem.addActionListener(new FrederickTrayIconActionListener());
        showItem.setActionCommand(FrederickTrayIconActionListener.SHOW_ETB_COMMAND);
        this.add(showItem);

        this.addSeparator();
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new FrederickTrayIconActionListener());
        exitItem.setActionCommand(FrederickTrayIconActionListener.EXIT_COMMAND);
        this.add(exitItem);
    }
    
}
