package de.turnertech.frederick.gui.map.feature;

import org.geotools.data.FeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.styling.Style;

public class BullseyeLayer extends FeatureLayer {

    public BullseyeLayer(FeatureSource<?,?> featureSource, Style style) {
        super(featureSource, style, "Bullseye");
    }

    @Override
    public void fireMapLayerListenerLayerChanged(int eventType) {
        super.fireMapLayerListenerLayerChanged(eventType);
    }
    
}
