package turnertech.frederick.gui.etb;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import turnertech.frederick.gui.InstantCellRenderer;

public class FrederickEtbTableColumnModel extends DefaultTableColumnModel {
    
    public FrederickEtbTableColumnModel() {
        super();
        TableColumn timestamp = new TableColumn(0);
        timestamp.setHeaderValue("Timestamp");
        timestamp.setMinWidth(130);
        timestamp.setMaxWidth(200);
        timestamp.setCellRenderer(new InstantCellRenderer());
        this.addColumn(timestamp);

        TableColumn user = new TableColumn(1);
        user.setHeaderValue("User");
        user.setMinWidth(30);
        user.setMaxWidth(120);
        user.setWidth(60);
        this.addColumn(user);

        TableColumn entry = new TableColumn(2);
        entry.setHeaderValue("Entry");
        this.addColumn(entry);

        TableColumn notes = new TableColumn(3);
        notes.setHeaderValue("Notes");
        notes.setWidth(200);
        this.addColumn(notes);
    }

}
