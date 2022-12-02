package de.turnertech.frederick.gui.tray;

import java.awt.TrayIcon;

import de.turnertech.frederick.Resources;


public class FrederickTrayIcon extends TrayIcon {

    public FrederickTrayIcon() {
        super(Resources.getThwIcon().getImage(), "Frederick", new FrederickTrayIconPopupMenu());
    }
    
}
