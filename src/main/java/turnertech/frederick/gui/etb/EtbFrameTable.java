package turnertech.frederick.gui.etb;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class EtbFrameTable extends JScrollPane {
    
    private final FrederickEtbTableModel dm;

    private final JTable etb;

    public EtbFrameTable(FrederickEtbTableModel dm) {
        super();

        JScrollPane scroll = this;
        
        this.dm = dm;

        etb = new JTable(dm, dm.getColumnModel());

        this.setViewportView(etb);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setMinimumSize(new Dimension(200,200));
    }
}
