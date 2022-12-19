package de.turnertech.frederick.gui.etb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.Database;
import de.turnertech.frederick.data.EtbEntry;

/**
 * This model is somewhat stateless. It uses the {@link Application} class
 * as its central service provider, and gets the data without caching. It
 * is more of a connector, than a data store.
 * 
 * Note, that this application does not aim to be modular!
 */
public class EtbTableModel extends AbstractTableModel implements ActionListener {

    public static final int TIMESTAMP = 0;

    public static final int USER = 1;

    public static final int ENTRY = 2;

    public static final int NOTES = 3;

    private final FrederickEtbTableColumnModel columnModel = new FrederickEtbTableColumnModel();

    public EtbTableModel() {
        Application.getDatabase().addActionListener(this);
    }

    @Override
    public int getRowCount() {
        return Application.getDatabase().getCurrentDeployment().getEtbEntryList().size();
    }

    @Override
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EtbEntry entry = Application.getDatabase().getCurrentDeployment().getEtbEntryList().get(rowIndex);
        if(columnIndex == TIMESTAMP) {
            return entry.getTimestamp();
        } else if (columnIndex == USER) {
            return entry.getUser();
        } else if (columnIndex == ENTRY) {
            return entry.getEntry();
        } else if (columnIndex == NOTES) {
            return entry.getNotes();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == NOTES;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == TIMESTAMP) {
            return Instant.class;
        } else if (columnIndex == USER) {
            return String.class;
        } else if (columnIndex == ENTRY) {
            return String.class;
        } else if (columnIndex == NOTES) {
            return String.class;
        }
        return Object.class;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        EtbEntry entry = Application.getDatabase().getCurrentDeployment().getEtbEntryList().get(rowIndex);
        if (columnIndex == NOTES) {
            entry.setNotes(aValue.toString());
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addEtbEntry(final EtbEntry etbEntry) {
        Application.getDatabase().getCurrentDeployment().getEtbEntryList().add(etbEntry);
        this.fireTableRowsInserted(Application.getDatabase().getCurrentDeployment().getEtbEntryList().size()-1, Application.getDatabase().getCurrentDeployment().getEtbEntryList().size()-1); 
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Database.DEPLOYMENT_CLOSED_EVENT.equals(e.getID())) {
            this.fireTableDataChanged();
        }
    }
    
}
