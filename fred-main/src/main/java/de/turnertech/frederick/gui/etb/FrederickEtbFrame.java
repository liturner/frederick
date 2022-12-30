package de.turnertech.frederick.gui.etb;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.turnertech.frederick.gui.status.StatusBar;
import de.turnertech.frederick.main.Resources;

public class FrederickEtbFrame extends JFrame {
    
    public FrederickEtbFrame() {
        super();
        this.setTitle("Frederick - ETB");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(800, 500);        
        this.add(new EtbFrameMainPanel(), BorderLayout.CENTER);
        this.add(new StatusBar(), BorderLayout.PAGE_END);

        this.addWindowListener(new FrederickEtbFrameEventListener(this));
    }

    void windowClosing() {
        this.setVisible(false);
    }

}
