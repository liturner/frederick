package de.turnertech.frederick.gui.map.browser;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.geotools.swing.JMapPane;

public class JPropertiesPane extends JPanel {
 
    public JPropertiesPane(JMapPane mapPane) {
        super(new GridBagLayout());
        this.setPreferredSize(new java.awt.Dimension(0, 300));
        this.setMinimumSize(new Dimension(0, 300));
        this.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Eigenschaften"),
                BorderFactory.createEmptyBorder(5,5,5,5))
        );

        this.add(new JMouseCoordinateUtmField(mapPane));
    }
    
}
