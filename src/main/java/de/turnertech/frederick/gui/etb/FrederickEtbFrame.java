package de.turnertech.frederick.gui.etb;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.turnertech.frederick.Resources;
import de.turnertech.frederick.gui.StatusBar;

public class FrederickEtbFrame extends JFrame {
    
    public FrederickEtbFrame() {
        super();
        this.setTitle("Frederick - ETB");
        this.setIconImage(Resources.getdiary24pxIcon().getImage());
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(800, 500);

        JMenuBar menu = new JMenuBar();
        menu.add(new JMenu("Test"));
        this.setJMenuBar(menu);
        
        this.add(new EtbFrameMainPanel(), BorderLayout.CENTER);
        this.add(new StatusBar(), BorderLayout.PAGE_END);

        this.addWindowListener(new FrederickEtbFrameEventListener(this));
    }

    void windowClosing() {
        this.setVisible(false);
    }

}
