package turnertech.frederick.gui.etb;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Instant;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import turnertech.frederick.Application;
import turnertech.frederick.data.EtbEntry;

public class EtbFrameTextEntry extends JScrollPane implements KeyListener {

    private final FrederickEtbTableModel tableModel;

    private final JTextArea textArea = new JTextArea();

    public EtbFrameTextEntry(FrederickEtbTableModel dm) {
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
            Application.save();
            e.consume();
        }   
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        // Not needed
    }
}
