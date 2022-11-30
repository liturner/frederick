package turnertech.frederick.gui.etb;

import javax.swing.JSplitPane;

import turnertech.frederick.Application;

public class EtbFrameMainPanel extends JSplitPane {
    
    public EtbFrameMainPanel() {
        super(JSplitPane.VERTICAL_SPLIT);

        EtbTableModel dm = new EtbTableModel(Application.getCurrentDeployment().getEtbEntryList());
        this.setTopComponent(new EtbFrameTable(dm));
        this.setBottomComponent(new EtbFrameTextEntry(dm));
        this.setResizeWeight(1.0);
    }
}
