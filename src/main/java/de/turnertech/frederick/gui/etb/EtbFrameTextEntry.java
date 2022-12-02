package de.turnertech.frederick.gui.etb;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Instant;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.data.EtbEntry;

public class EtbFrameTextEntry extends JScrollPane implements KeyListener {

    private final EtbTableModel tableModel;

    private final JTextArea textArea = new JTextArea();

    public EtbFrameTextEntry(EtbTableModel dm) {
        super();
        this.setViewportView(textArea);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setMinimumSize(new Dimension(10, 100));

        tableModel = dm;
        textArea.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) { 
        // Not needed 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown() ) {
            tableModel.addEtbEntry(new EtbEntry(Instant.now(), Application.CURRENT_USER, textArea.getText()));    
            textArea.setText("");
            Application.getDatabase().saveCurrentDeployment();;
            e.consume();
        }   
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        // Not needed
    }
}
