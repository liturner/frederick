package de.turnertech.frederick.gui.map.action;
import java.awt.event.ActionEvent;
import java.util.Optional;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.swing.MapPane;
import org.geotools.swing.action.MapAction;

import de.turnertech.frederick.Logging;
import de.turnertech.frederick.gui.map.feature.BullseyeLayer;

public class FocusBullseyeAction extends MapAction {
    
    public static final String ICON_IMAGE = "/org/geotools/swing/icons/mActionZoomFullExtent.png";

    public FocusBullseyeAction(MapPane mapPane) {
        super.init(mapPane, "Bullseye Zentrieren", "Bullseye Zentrieren", ICON_IMAGE);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Optional<ReferencedEnvelope> bullseye = BullseyeLayer.getArea(getMapPane());
        if(bullseye.isEmpty()) {
            Logging.LOGGER.warning("Bullseye not set. Cannot focus on bullseye untill it is set.");
            return;
        }
        getMapPane().setDisplayArea(bullseye.get());
    }

}
