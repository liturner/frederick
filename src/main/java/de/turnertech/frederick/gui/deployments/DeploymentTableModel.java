package de.turnertech.frederick.gui.deployments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Instant;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.Database;
import de.turnertech.frederick.gui.InstantCellRenderer;

public class DeploymentTableModel extends AbstractTableModel implements ActionListener  {

    public static final int DATE = 0;

    public static final int NAME = 1;

    private final DefaultTableColumnModel columnModel = new DefaultTableColumnModel();

    public DeploymentTableModel() {

        // Prepare Column Model
        Application.getDatabase().addActionListener(this);

        TableColumn date = new TableColumn(DATE);
        date.setHeaderValue("Date");
        date.setMinWidth(130);
        date.setMaxWidth(200);
        date.setCellRenderer(new InstantCellRenderer());
        columnModel.addColumn(date);

        TableColumn timestamp = new TableColumn(NAME);
        timestamp.setHeaderValue("Einsatz");
        timestamp.setMinWidth(130);
        columnModel.addColumn(timestamp);

    }

    @Override
    public int getRowCount() {
        return Application.getDatabase().getDeploymentFiles().size();
    }

    @Override
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == DATE) {
            return Object.class;
        } else if (columnIndex == NAME) {
            return String.class;
        }
        return Object.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        File file = Application.getDatabase().getDeploymentFiles().get(rowIndex);
        if(columnIndex == DATE) {
            return Instant.ofEpochMilli(file.lastModified());
        } else if (columnIndex == NAME) {
            return file.getName();
        }

        return null;
    }

    public TableColumnModel getColumnModel() {
        return columnModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Database.DEPLOYMENT_CLOSED_EVENT.equals(e.getID())) {
            fireTableDataChanged();
        } else if(Database.DEPLOYMENT_SAVED_EVENT.equals(e.getID())) {
            fireTableDataChanged();
        }
    }
    
}
