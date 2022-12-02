package de.turnertech.frederick;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Basic helper class intended to make the management and use of resources throughout the allication
 * simple and efficient.
 */
public class Resources {

    private static URL thwIconUrl;
    
    private static ImageIcon thwIcon;

    private Resources() {

    }

    public static URL getThwIconUrl() {
        if(thwIconUrl == null) {
            thwIconUrl = Application.class.getResource("/gui/thw.png");
        }
        return thwIconUrl;
    }

    public static ImageIcon getThwIcon() {
        if(thwIcon == null) {
            thwIcon = new ImageIcon(Resources.getThwIconUrl());
        }
        return thwIcon;
    }

}
