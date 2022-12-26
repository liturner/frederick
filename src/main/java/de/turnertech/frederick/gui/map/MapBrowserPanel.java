package de.turnertech.frederick.gui.map;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.geotools.swing.JMapPane;

import de.turnertech.frederick.Resources;
import de.turnertech.frederick.gui.map.browser.JPropertiesPane;

/**
 * A major panel containing usefull information about the map or selected object,
 * as well as the ability to add new elements to the map.
 */
public class MapBrowserPanel extends JPanel {
    
    public MapBrowserPanel(JMapPane mapPane) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new java.awt.Dimension(400, 0));

        JPanel propertiesPane = new JPropertiesPane(mapPane);
        this.add(propertiesPane);

        // TODO: Create an Element panel with drag and drop elements
        DefaultListModel<ImageIcon> listModel = new DefaultListModel<>();
        JList<ImageIcon> elementsPane = new JList<>(listModel);
        elementsPane.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        elementsPane.setVisibleRowCount(-1);
        for (int row = 0; row < 100; ++row) {
            listModel.addElement(Resources.getHelp24pxIcon());
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

}
