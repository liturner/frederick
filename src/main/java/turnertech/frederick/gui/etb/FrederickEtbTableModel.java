package turnertech.frederick.gui.etb;

import java.time.Instant;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

public class FrederickEtbTableModel extends AbstractTableModel {

    private final LinkedList<FrederickEtbEntry> etbEntries = new LinkedList<>();

    private final FrederickEtbTableColumnModel columnModel = new FrederickEtbTableColumnModel();

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
        FrederickEtbEntry entry = etbEntries.get(rowIndex);
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
        FrederickEtbEntry entry = etbEntries.get(rowIndex);
        if(columnIndex == 4) {

        }
    }

    public void addEtbEntry(final FrederickEtbEntry etbEntry) {
        etbEntries.add(etbEntry);
        this.fireTableRowsInserted(etbEntries.size()-1, etbEntries.size()-1); 
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }
    
}
