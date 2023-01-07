package de.turnertech.frederick.gui.common;

import java.awt.Component;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class InstantCellRenderer extends DefaultTableCellRenderer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault());

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        if(value instanceof Date) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setText(formatter.format(((Date) value).toInstant()));            
            return this;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
