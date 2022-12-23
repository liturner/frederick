package de.turnertech.frederick.gui.map.browser;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.measure.AngleFormat;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseAdapter;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.event.MapPaneAdapter;
import org.geotools.swing.event.MapPaneEvent;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class JMouseCoordinateField extends JPanel {
    
    private final JLabel label;

    private final JLabel content;

    private final AngleFormat angleFormat;

    public JMouseCoordinateField(JMapPane mapPane) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel("Mouse Position");
        content = new JLabel();
        add(label);
        add(content);

        angleFormat = new AngleFormat("DD°MM.mmm'");

        mapPane.addMouseListener(
                new MapMouseAdapter() {
                    @Override
                    public void onMouseEntered(MapMouseEvent ev) {
                        CoordinateReferenceSystem sourceCRS = ev.getSource().getMapContent().getCoordinateReferenceSystem();
                        CoordinateReferenceSystem targetCRS = DefaultGeographicCRS.WGS84;
                        try {
                            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
                            DirectPosition2D posOut = new DirectPosition2D();
                            transform.transform(ev.getWorldPos(), posOut);
                            displayCoords(posOut);
                        } catch (FactoryException | MismatchedDimensionException | TransformException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                    }

                    @Override
                    public void onMouseExited(MapMouseEvent ev) {
                        displayNoCursor();
                    }

                    @Override
                    public void onMouseMoved(MapMouseEvent ev) {
                        CoordinateReferenceSystem sourceCRS = ev.getSource().getMapContent().getCoordinateReferenceSystem();
                        CoordinateReferenceSystem targetCRS = DefaultGeographicCRS.WGS84;
                        try {
                            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
                            DirectPosition2D posOut = new DirectPosition2D();
                            transform.transform(ev.getWorldPos(), posOut);
                            displayCoords(posOut);
                        } catch (FactoryException | MismatchedDimensionException | TransformException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

        mapPane.addMapPaneListener(
                new MapPaneAdapter() {
                    @Override
                    public void onDisplayAreaChanged(MapPaneEvent ev) {
                        //setFormat(((ReferencedEnvelope) ev.getData()));
                    }
                });

        displayNoCursor();
    }

    private void displayNoCursor() {
        content.setText("E00°00,000' N00°00,000'");
    }

    private void displayCoords(DirectPosition2D p) {
        content.setText(String.format("E%s N%s", angleFormat.format(p.getX()), angleFormat.format(p.getY())));
    }

}
