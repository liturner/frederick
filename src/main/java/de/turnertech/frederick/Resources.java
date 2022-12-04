package de.turnertech.frederick;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Basic helper class intended to make the management and use of resources throughout the allication
 * simple and efficient.
 */
public class Resources {

    private Resources() {

    }
    
    private static ImageIcon deployment16pxIcon;
    public static ImageIcon getdeployment16pxIcon() {
        if(deployment16pxIcon == null) {
            URL iconUrl = Application.class.getResource("icon-16.png");
            deployment16pxIcon = new ImageIcon(iconUrl);
        }
        return deployment16pxIcon;
    }

    private static ImageIcon deployment24pxIcon;
    public static ImageIcon getdeployment24pxIcon() {
        if(deployment24pxIcon == null) {
            URL iconUrl = Application.class.getResource("icon-24.png");
            deployment24pxIcon = new ImageIcon(iconUrl);
        }
        return deployment24pxIcon;
    }

    private static ImageIcon deployment32pxIcon;
    public static ImageIcon getdeployment32pxIcon() {
        if(deployment32pxIcon == null) {
            URL iconUrl = Application.class.getResource("icon-32.png");
            deployment32pxIcon = new ImageIcon(iconUrl);
        }
        return deployment32pxIcon;
    }
    

}
