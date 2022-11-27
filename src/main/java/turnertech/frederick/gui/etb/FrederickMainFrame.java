package turnertech.frederick.gui.etb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import turnertech.frederick.Application;

public class FrederickMainFrame extends JFrame {
    
    public FrederickMainFrame() {
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(500, 500);

        FrederickEtbTableModel dm = new FrederickEtbTableModel();
        JTable etb = new JTable(dm, dm.getColumnModel());
        JScrollPane sp = new JScrollPane(etb);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(sp, BorderLayout.CENTER);

        JTextField textInput = new JTextField();
        textInput.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dm.addEtbEntry(new FrederickEtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));    
            }
            
        });
        this.add(textInput, BorderLayout.PAGE_END);

        this.addWindowListener(new FrederickMainFrameEventListener(this));

        dm.addEtbEntry(new FrederickEtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
        dm.addEtbEntry(new FrederickEtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
        dm.addEtbEntry(new FrederickEtbEntry(Instant.now(), Application.CURRENT_USER, textInput.getText()));
    }

    void windowClosing() {
        this.setVisible(false);
    }

}
