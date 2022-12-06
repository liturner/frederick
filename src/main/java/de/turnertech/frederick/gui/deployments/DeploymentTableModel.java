package de.turnertech.frederick.gui.deployments;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class DeploymentTableModel extends AbstractTableModel  {

    public static final int DATE = 0;

    public static final int NAME = 1;

    private final DefaultTableColumnModel columnModel = new DefaultTableColumnModel();

    public DeploymentTableModel() {

        // Prepare Column Model

        TableColumn date = new TableColumn(DATE);
        date.setHeaderValue("Date");
        date.setMinWidth(130);
        columnModel.addColumn(date);

        TableColumn timestamp = new TableColumn(NAME);
        timestamp.setHeaderValue("Einsatz");
        timestamp.setMinWidth(130);
        columnModel.addColumn(timestamp);

    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }
    
}
