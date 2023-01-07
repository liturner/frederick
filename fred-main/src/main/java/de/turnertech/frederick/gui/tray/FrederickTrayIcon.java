package de.turnertech.frederick.gui.tray;

import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import de.turnertech.frederick.main.Application;
import de.turnertech.frederick.services.Resources;


public class FrederickTrayIcon extends TrayIcon implements MouseListener  {

    public FrederickTrayIcon() {
        super(Resources.getdeployment24pxIcon().getImage(), "Frederick", new FrederickTrayIconPopupMenu());
        this.setImageAutoSize(true);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            Application.getEtbFrame().setVisible(true);
            e.consume();
        }
    }

    @Override public void mousePressed(MouseEvent e) {/* Not in use */}
    @Override public void mouseReleased(MouseEvent e) {/* Not in use */}
    @Override public void mouseEntered(MouseEvent e) {/* Not in use */}
    @Override public void mouseExited(MouseEvent e) {/* Not in use */}
    
}
