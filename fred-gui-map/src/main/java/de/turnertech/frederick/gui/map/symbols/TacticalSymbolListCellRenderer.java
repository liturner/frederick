package de.turnertech.frederick.gui.map.symbols;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.turnertech.tz.swing.TacticalSymbol;

public class TacticalSymbolListCellRenderer extends DefaultListCellRenderer {
    
    private final int renderWidth;

    private final int renderHeight;

    public TacticalSymbolListCellRenderer(final int renderWidth, final int renderHeight) {
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        setOpaque(true);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object tacticalSymbolObject, int index, boolean isSelected, boolean cellHasFocus) { 
        JLabel returnComponent = (JLabel)super.getListCellRendererComponent(list, tacticalSymbolObject, index, isSelected, cellHasFocus);
        TacticalSymbol tacticalSymbol = (TacticalSymbol)tacticalSymbolObject;
        
        returnComponent.setIcon(tacticalSymbol.getImageIcon(renderWidth, renderHeight));
        returnComponent.setText(null);
        returnComponent.setToolTipText(tacticalSymbol.toString());
 
        return this;
    }

}
