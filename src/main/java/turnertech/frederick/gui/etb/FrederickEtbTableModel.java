package turnertech.frederick.gui.etb;

import java.time.Instant;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import turnertech.frederick.data.EtbEntry;

public class FrederickEtbTableModel extends AbstractTableModel {

    private final transient List<EtbEntry> etbEntries;

    private final FrederickEtbTableColumnModel columnModel = new FrederickEtbTableColumnModel();

    public FrederickEtbTableModel(final List<EtbEntry> source) {
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
        if(columnIndex == 0) {
            return entry.getTimestamp();
        } else if (columnIndex == 1) {
            return entry.getUser();
        } else if (columnIndex == 2) {
            return entry.getEntry();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0) {
            return Instant.class;
        } else if (columnIndex == 1) {
            return String.class;
        } else if (columnIndex == 2) {
            return String.class;
        }
        return Object.class;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Not yet implemented
    }

    public void addEtbEntry(final EtbEntry etbEntry) {
        etbEntries.add(etbEntry);
        this.fireTableRowsInserted(etbEntries.size()-1, etbEntries.size()-1); 
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }
    
}
