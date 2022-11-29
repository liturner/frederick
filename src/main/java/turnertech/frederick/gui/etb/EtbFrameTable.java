package turnertech.frederick.gui.etb;

import java.awt.Dimension;
import java.time.Instant;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import turnertech.frederick.gui.InstantCellRenderer;

public class EtbFrameTable extends JScrollPane {
    
    private final FrederickEtbTableModel dm;

    private final JTable etb;

    public EtbFrameTable(FrederickEtbTableModel dm) {
        super();

        JScrollPane scroll = this;
        
        this.dm = dm;

        etb = new JTable(dm, dm.getColumnModel());
        etb.setDefaultRenderer(Instant.class, new InstantCellRenderer());
        etb.setDefaultRenderer(String.class, new MultiLineCellRenderer(dm));

        this.setViewportView(etb);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setMinimumSize(new Dimension(200,200));
    }
}
