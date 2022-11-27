package turnertech.frederick.gui;

import java.awt.TrayIcon;

import javax.swing.ImageIcon;

import turnertech.frederick.Resources;


public class FrederickTrayIcon extends TrayIcon {

    private static ImageIcon icon = new ImageIcon(Resources.THW_ICON_URL);

    public FrederickTrayIcon() {
        super(icon.getImage());
    }
    
}
