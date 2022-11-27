package turnertech.frederick.gui.etb;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class FrederickEtbTableColumnModel extends DefaultTableColumnModel {
    
    public FrederickEtbTableColumnModel() {
        super();
        TableColumn timestamp = new TableColumn(0);
        timestamp.setHeaderValue("Timestamp");
        timestamp.setMinWidth(100);
        timestamp.setMaxWidth(200);
        timestamp.setWidth(120);
        this.addColumn(timestamp);

        TableColumn user = new TableColumn(1);
        user.setHeaderValue("User");
        this.addColumn(user);

        TableColumn entry = new TableColumn(2);
        entry.setHeaderValue("Entry");
        this.addColumn(entry);

        TableColumn notes = new TableColumn(3);
        notes.setHeaderValue("Notes");
        this.addColumn(notes);
    }

}
