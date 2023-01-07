package de.turnertech.frederick.gui.diary;

import javax.swing.JSplitPane;

public class EtbFrameMainPanel extends JSplitPane {
    
    public EtbFrameMainPanel() {
        super(JSplitPane.VERTICAL_SPLIT);

        EtbTableModel dm = new EtbTableModel();
        this.setTopComponent(new EtbFrameTable(dm));
        this.setBottomComponent(new EtbFrameTextEntry(dm));
        this.setResizeWeight(1.0);
    }
}
