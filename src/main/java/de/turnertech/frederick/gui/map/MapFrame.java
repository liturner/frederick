package de.turnertech.frederick.gui.map;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.grid.Lines;
import org.geotools.grid.ortholine.LineOrientation;
import org.geotools.grid.ortholine.OrthoLineDef;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.tile.TileService;
import org.geotools.tile.impl.osm.OSMService;
import org.geotools.tile.util.TileLayer;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import de.turnertech.frederick.Logging;
import de.turnertech.frederick.Resources;

public class MapFrame extends JMapFrame {
    
    private final ReferencedEnvelope germanyEnvelope = new ReferencedEnvelope(5.0, 16, 47.0, 55.0, DefaultGeographicCRS.WGS84);

    public MapFrame() {
        MapContent map = new MapContent();
        String baseURL = "http://tile.openstreetmap.org/";
        TileService service = new OSMService("OSM", baseURL);
        TileLayer layer = new TileLayer(service);
        
        try {
            CoordinateReferenceSystem crs = CRS.decode("EPSG:3857");
            map.getViewport().setBounds(germanyEnvelope);
            map.getViewport().setCoordinateReferenceSystem(crs);
        } catch (FactoryException e) {
            Logging.LOGGER.severe("Could not decode CRS");
        }

        map.addLayer(layer);
        addGrids(map);

        this.setTitle("Frederick - Einsatz Karte");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        this.setMapContent(map);
        this.enableStatusBar(true);
        this.enableToolBar(true);
        this.initComponents();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void addGrids(MapContent map) {
        // Specify vertex spacing to get "densified" polygons
        double vertexSpacing = 0.1;

        /*
        * Line definitions:
        * major lines at 10 degree spacing are indicated by level = 2
        * minor lines at 2 degree spacing are indicated by level = 1
        * (level values are arbitrary; only rank order matters)
        */
        List<OrthoLineDef> majorGridLines =
                Arrays.asList(
                        new OrthoLineDef(LineOrientation.VERTICAL, 2, 1.0),
                        new OrthoLineDef(LineOrientation.HORIZONTAL, 2, 1.0));

        SimpleFeatureSource majorGridSource = Lines.createOrthoLines(germanyEnvelope, majorGridLines, vertexSpacing);
        Style majorGridStyle = SLD.createLineStyle( Color.BLACK, 2.0f );
        Layer majorGridLayer = new FeatureLayer(majorGridSource, majorGridStyle);
        map.addLayer(majorGridLayer);

        List<OrthoLineDef> minorLines =
                Arrays.asList(
                        new OrthoLineDef(LineOrientation.VERTICAL, 1, 1.0 / 60.0),
                        new OrthoLineDef(LineOrientation.HORIZONTAL, 1, 1.0 / 60.0));

        SimpleFeatureSource minorGridSource = Lines.createOrthoLines(germanyEnvelope, minorLines, vertexSpacing);
        Style minorGridStyle = SLD.createLineStyle( Color.BLACK, 0.5f );
        for(FeatureTypeStyle fts : minorGridStyle.featureTypeStyles()) {
            fts.rules().get(0).setMaxScaleDenominator(500000.0);
            fts.rules().get(0).setMinScaleDenominator(0.0);
        }

        Layer minorGridLayer = new FeatureLayer(minorGridSource, minorGridStyle);
        map.addLayer(minorGridLayer);
    }

}
