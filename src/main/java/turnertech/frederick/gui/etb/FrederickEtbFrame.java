package turnertech.frederick.gui.etb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Instant;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import turnertech.frederick.Application;
import turnertech.frederick.Resources;
import turnertech.frederick.data.EtbEntry;
import turnertech.frederick.gui.StatusBar;

public class FrederickEtbFrame extends JFrame {
    
    public FrederickEtbFrame() {
        this.setTitle("Frederick - ETB");
        this.setIconImage(Resources.getThwIcon().getImage());
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(500, 500);

        JMenuBar menu = new JMenuBar();
        menu.add(new JMenu("Test"));
        this.setJMenuBar(menu);

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        FrederickEtbTableModel dm = new FrederickEtbTableModel(Application.getCurrentDeployment().getEtbEntryList());
        JTable etb = new JTable(dm, dm.getColumnModel());

        JScrollPane tableScroll = new JScrollPane(etb);
        tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listPane.add(tableScroll);

        JTextArea textInput = new JTextArea();
        textInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { 
                // Not needed 
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown() ) {
                    dm.addEtbEntry(new EtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));    
                    textInput.setText("");
                    e.consume();
                }   
            }

            @Override
            public void keyReleased(KeyEvent e) { 
                // Not needed
            }
            
        });
        JScrollPane textScroll = new JScrollPane(textInput);
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textScroll.setMinimumSize(new Dimension(0, 200));
        listPane.add(textScroll);
        this.add(listPane, BorderLayout.CENTER);
        this.add(new StatusBar(), BorderLayout.PAGE_END);

        this.addWindowListener(new FrederickEtbFrameEventListener(this));

        dm.addEtbEntry(new EtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
        dm.addEtbEntry(new EtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
        dm.addEtbEntry(new EtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
    }

    void windowClosing() {
        this.setVisible(false);
    }

}
