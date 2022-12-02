package de.turnertech.frederick.gui.etb;

import java.time.Instant;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import de.turnertech.frederick.data.EtbEntry;

public class EtbTableModel extends AbstractTableModel {

    public static final int TIMESTAMP = 0;

    public static final int USER = 1;

    public static final int ENTRY = 2;

    public static final int NOTES = 3;

    private final transient List<EtbEntry> etbEntries;

    private final FrederickEtbTableColumnModel columnModel = new FrederickEtbTableColumnModel();

    public EtbTableModel(final List<EtbEntry> source) {
        if(source == null) {
            throw new IllegalArgumentException("Table model cannot be initialised without a data source.");
        }
        this.etbEntries = source;
    }

    @Override
    public int getRowCount() {
        return etbEntries.size();
    }

    @Override
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EtbEntry entry = etbEntries.get(rowIndex);
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
        EtbEntry entry = etbEntries.get(rowIndex);
        if (columnIndex == NOTES) {
            entry.setNotes(aValue.toString());
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addEtbEntry(final EtbEntry etbEntry) {
        etbEntries.add(etbEntry);
        this.fireTableRowsInserted(etbEntries.size()-1, etbEntries.size()-1); 
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }
    
}
