package turnertech.frederick.gui.tray;

import java.awt.TrayIcon;

import turnertech.frederick.Resources;


public class FrederickTrayIcon extends TrayIcon {

    public FrederickTrayIcon() {
        super(Resources.getThwIcon().getImage(), "Frederick", new FrederickTrayIconPopupMenu());
    }
    
}
