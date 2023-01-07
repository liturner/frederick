package de.turnertech.frederick.services;

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
            URL iconUrl = Resources.class.getResource("icon-16.png");
            deployment16pxIcon = new ImageIcon(iconUrl);
        }
        return deployment16pxIcon;
    }

    private static ImageIcon deployment24pxIcon;
    public static ImageIcon getdeployment24pxIcon() {
        if(deployment24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icon-24.png");
            deployment24pxIcon = new ImageIcon(iconUrl);
        }
        return deployment24pxIcon;
    }

    private static ImageIcon deployment32pxIcon;
    public static ImageIcon getdeployment32pxIcon() {
        if(deployment32pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icon-32.png");
            deployment32pxIcon = new ImageIcon(iconUrl);
        }
        return deployment32pxIcon;
    }
    
    private static ImageIcon rowDelete24pxIcon;
    public static ImageIcon getRowDelete24pxIcon() {
        if(rowDelete24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Remove24.png");
            rowDelete24pxIcon = new ImageIcon(iconUrl);
        }
        return rowDelete24pxIcon;
    }

    private static ImageIcon print24pxIcon;
    public static ImageIcon getPrint24pxIcon() {
        if(print24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Print24.png");
            print24pxIcon = new ImageIcon(iconUrl);
        }
        return print24pxIcon;
    }

    private static ImageIcon delete24pxIcon;
    public static ImageIcon getDelete24pxIcon() {
        if(delete24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Delete24.png");
            delete24pxIcon = new ImageIcon(iconUrl);
        }
        return delete24pxIcon;
    }

    private static ImageIcon export24pxIcon;
    public static ImageIcon getExport24pxIcon() {
        if(export24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Share24.png");
            export24pxIcon = new ImageIcon(iconUrl);
        }
        return export24pxIcon;
    }

    private static ImageIcon stop24pxIcon;
    public static ImageIcon getStop24pxIcon() {
        if(stop24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Close24.png");
            stop24pxIcon = new ImageIcon(iconUrl);
        }
        return stop24pxIcon;
    }

    private static ImageIcon help24pxIcon;
    public static ImageIcon getHelp24pxIcon() {
        if(help24pxIcon == null) {
            URL iconUrl = Resources.class.getResource("icons/Help24.png");
            help24pxIcon = new ImageIcon(iconUrl);
        }
        return help24pxIcon;
    }

}
