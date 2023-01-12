package de.turnertech.frederick.gui.map;

import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.geotools.swing.JMapPane;

import de.turnertech.frederick.gui.map.browser.JPropertiesPane;
import de.turnertech.frederick.gui.map.symbols.TacticalSymbolListCellRenderer;
import de.turnertech.frederick.services.Logging;
import de.turnertech.tz.swing.TacticalSymbol;

/**
 * A major panel containing usefull information about the map or selected object,
 * as well as the ability to add new elements to the map.
 */
public class MapBrowserPanel extends JPanel implements DragGestureListener {
    
    JList<TacticalSymbol> elementsPane;

    public MapBrowserPanel(JMapPane mapPane) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.setPreferredSize(new java.awt.Dimension(400, 0));

        JPanel propertiesPane = new JPropertiesPane(mapPane);
        this.add(propertiesPane);

        // TODO: Create an Element panel with drag and drop elements
        DefaultListModel<TacticalSymbol> listModel = new DefaultListModel<>();
        elementsPane = new JList<>(listModel);
        elementsPane.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        elementsPane.setVisibleRowCount(-1);
        elementsPane.setCellRenderer(new TacticalSymbolListCellRenderer(48, 48));
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(elementsPane, DnDConstants.ACTION_COPY, this);
        
        Collection<TacticalSymbol> symbols = de.turnertech.tz.swing.TacticalSymbolFactory.getTacticalSymbols();
        for(TacticalSymbol symbol : symbols) {
            listModel.addElement(symbol);
        }

        JScrollPane selementsScrollPane = new JScrollPane(elementsPane);
        selementsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        selementsScrollPane.setMinimumSize(new Dimension(200,200));
        selementsScrollPane.setPreferredSize(new Dimension(200, Integer.MAX_VALUE));
        
        JPanel elementsGroup = new JPanel();
        elementsGroup.setLayout(new BoxLayout(elementsGroup, BoxLayout.Y_AXIS));
        elementsGroup.add(new JTextField());
        elementsGroup.add(selementsScrollPane);
        elementsGroup.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Lagekarte Elemente"),
                BorderFactory.createEmptyBorder(0,0,0,0)));

        this.add(elementsGroup);
    }

    // https://zetcode.com/javaswing/draganddrop/
    // This should be part of the JList class we implement. It has to know whats selected.
    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Logging.LOGGER.info("DragGestureListener.dragGestureRecognized");
        
        // "this" will be replaced by the actuall object.
        // ToDo, I assume here is where we can set the cursor to include the imageIcon!
        event.startDrag(DragSource.DefaultCopyDrop, elementsPane.getSelectedValue());
    }


}
