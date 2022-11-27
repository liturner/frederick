package turnertech.frederick.gui;

import java.awt.Image;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;

import turnertech.frederick.Resources;


public class FrederickTrayIcon extends TrayIcon {

    private static Image icon = new ImageIcon(Resources.THW_ICON_URL).getImage();

    public FrederickTrayIcon() {
        super(icon, "Frederick", new FrederickTrayIconPopupMenu());
    }
    
}
