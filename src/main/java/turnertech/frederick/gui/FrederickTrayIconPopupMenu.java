package turnertech.frederick.gui;

import java.awt.MenuItem;
import java.awt.PopupMenu;


public class FrederickTrayIconPopupMenu extends PopupMenu {

    public FrederickTrayIconPopupMenu() {
        super();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new FredereickExitClickedActionListener());
        this.add(exitItem);
    }
    
}
