package turnertech.frederick.gui.etb;

import javax.swing.DefaultListSelectionModel;

public class EtbTableSelectionModel extends DefaultListSelectionModel {
    
    public EtbTableSelectionModel() {
        this.setSelectionMode(SINGLE_SELECTION);
    }

}
