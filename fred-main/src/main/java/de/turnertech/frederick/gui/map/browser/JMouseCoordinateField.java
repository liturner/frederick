package de.turnertech.frederick.gui.map.browser;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapBoundsEvent;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseAdapter;
import org.geotools.swing.event.MapMouseEvent;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import de.turnertech.frederick.gui.map.MapHelper;
import de.turnertech.frederick.services.Logging;

/**
 * A (deliberately) non configurable field to display the current position of
 * the mouse cursor when over the map pane. The display output is a set of
 * coordinate formats which cannot be changed.
 */
public class JMouseCoordinateField extends JPanel {
    
    private final JLabel label;

    private final JLabel content;

    private DirectPosition2D position;

    private transient MathTransform transform;

    /**
     * The only expected initialiser.
     * 
     * @param mapPane The {@link JMapPane} to which this field should listen to updates from.
     */
    public JMouseCoordinateField(final JMapPane mapPane) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Mouse Position");
        position = new DirectPosition2D(mapPane.getMapContent().getCoordinateReferenceSystem(), 0.0, 0.0);
        content = new JLabel();
        updateCoordinateTransform(mapPane.getMapContent().getCoordinateReferenceSystem());
        updateContent();
        add(label);
        add(content);

        mapPane.getMapContent().addMapBoundsListener(mapBoundsEvent -> {
                if(mapBoundsEvent.getEventType().contains(MapBoundsEvent.Type.CRS)) {
                    updateCoordinateTransform(mapBoundsEvent.getNewCoordinateReferenceSystem());
                }
            }
        );

        mapPane.addMouseListener(
            new MapMouseAdapter() {
                @Override
                public void onMouseEntered(MapMouseEvent ev) {
                    updatePosition(ev);                    
                }

                @Override
                public void onMouseExited(MapMouseEvent ev) {
                    position.setLocation(0.0, 0.0);
                    updateContent();
                }

                @Override
                public void onMouseMoved(MapMouseEvent ev) {
                    updatePosition(ev);
                }
            }
        );
    }

    /**
     * The math transform is cached. Calling this function will cause that transform
     * to be updated. This should be called whenever the CRS of the world changes.
     * 
     * @param sourceCRS The source CRS to be assumed for subsequent calls to {@link JMouseCoordinateField#updatePosition}
     */
    private void updateCoordinateTransform(final CoordinateReferenceSystem sourceCRS) {
        try {
            this.transform = CRS.findMathTransform(sourceCRS, DefaultGeographicCRS.WGS84, true);
        } catch (FactoryException e) {
            Logging.LOGGER.severe("Could not create CRS transform. Coordinates may be false!");
        }
    }

    /**
     * Updates the cached position with the value of the mouse position in the 
     * mapMouseEvent parameter. This function will trigger a transformation
     * using the cached transform object.
     * 
     * @param mapMouseEvent The {@link MapMouseEvent} as delivered from {@link MapContent#addMapBoundsListener}
     */
    private void updatePosition(final MapMouseEvent mapMouseEvent) {
        try {
            transform.transform(mapMouseEvent.getWorldPos(), position);
        } catch (MismatchedDimensionException | TransformException e) {
            Logging.LOGGER.severe("Could not transform coordinate. Coordinates may be false!");
        }
        updateContent();
    }

    /**
     * Updates the output text of the component.
     */
    private void updateContent() {
        this.content.setText(MapHelper.format(position));
    }

}
