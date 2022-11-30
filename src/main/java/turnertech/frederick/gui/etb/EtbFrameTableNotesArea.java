package turnertech.frederick.gui.etb;

import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import turnertech.frederick.Application;

public class EtbFrameTableNotesArea extends JTextArea implements ListSelectionListener,  DocumentListener {
    
    private final EtbTableModel tableModel;

    private final EtbTableSelectionModel selectionModel;

    public EtbFrameTableNotesArea(EtbTableModel tableModel, EtbTableSelectionModel selectionModel) {
        this.tableModel = tableModel;
        this.selectionModel = selectionModel;
        this.getDocument().addDocumentListener(this);
        this.setMinimumSize(new Dimension(200,200));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) {
            Application.save();
            return;
        }
        this.getDocument().removeDocumentListener(this);
        this.setText(tableModel.getValueAt(selectionModel.getLeadSelectionIndex(), EtbTableModel.NOTES).toString());
        this.getDocument().addDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.changedUpdate(e);        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        tableModel.setValueAt(this.getText(), selectionModel.getLeadSelectionIndex(), EtbTableModel.NOTES);
    }

}
