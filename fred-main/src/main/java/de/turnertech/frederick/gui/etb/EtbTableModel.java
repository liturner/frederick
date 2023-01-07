package de.turnertech.frederick.gui.etb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import de.turnertech.frederick.data.EtbEntry;
import de.turnertech.frederick.services.ActionService;
import de.turnertech.frederick.services.ApplicationService;
import de.turnertech.frederick.services.PersistanceProvider;

/**
 * This model is somewhat stateless. It uses the {@link ApplicationService} class
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
        ActionService.addActionListener(this);
    }

    @Override
    public int getRowCount() {
        return PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().size();
    }

    @Override
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EtbEntry entry = PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().get(rowIndex);
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
            return Date.class;
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
        EtbEntry entry = PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().get(rowIndex);
        if (columnIndex == NOTES) {
            entry.setNotes(aValue.toString());
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addEtbEntry(final EtbEntry etbEntry) {
        PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().add(etbEntry);
        this.fireTableRowsInserted(PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().size()-1, PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().size()-1); 
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(PersistanceProvider.DEPLOYMENT_CLOSED_EVENT_ID == e.getID() || PersistanceProvider.DEPLOYMENT_UPDATED_EVENT_ID == e.getID()) {
            this.fireTableDataChanged();
        }
    }
    
}
