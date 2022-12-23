package de.turnertech.frederick.gui.map.browser;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.geotools.swing.JMapPane;

public class JPropertiesPane extends JPanel {
 
    public JPropertiesPane(JMapPane mapPane) {
        super(new GridBagLayout());
        
        this.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Eigenschaften"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
    }
    
}
