package turnertech.frederick.gui;

import java.awt.Component;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class InstantCellRenderer extends DefaultTableCellRenderer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault());

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        if(value instanceof Instant) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setText(formatter.format((Instant) value));            
            return this;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
}
