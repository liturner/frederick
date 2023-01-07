package de.turnertech.frederick.gui.diary;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class EtbFrameTable extends JSplitPane {
    
    private final JTable etb;

    public EtbFrameTable(EtbTableModel dm) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        
        EtbTableSelectionModel selectionModel = new EtbTableSelectionModel();
        EtbFrameTableNotesArea notesArea = new EtbFrameTableNotesArea(dm, selectionModel);
        
        selectionModel.addListSelectionListener(notesArea);
        etb = new JTable(dm, dm.getColumnModel(), selectionModel);

        JScrollPane tableScroll = new JScrollPane(etb);
        tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroll.setMinimumSize(new Dimension(200,200));

        this.setLeftComponent(tableScroll);
        this.setRightComponent(notesArea);
        this.setResizeWeight(1.0);
    }
}
