package de.turnertech.frederick.gui.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

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
import org.geotools.swing.JMapPane;
import org.geotools.swing.MapLayerTable;
import org.geotools.tile.TileService;
import org.geotools.tile.impl.osm.OSMService;
import org.geotools.tile.util.TileLayer;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import de.turnertech.frederick.Logging;
import de.turnertech.frederick.Resources;
import de.turnertech.frederick.gui.map.feature.Bullseye;
import de.turnertech.frederick.gui.map.tool.ContextMenuTool;
import de.turnertech.frederick.gui.map.tool.PanTool;
import de.turnertech.frederick.gui.map.tool.ScrollTool;
import de.turnertech.frederick.gui.status.StatusBar;

public class MapFrameTake2 extends JFrame {
    
    private final ReferencedEnvelope germanyEnvelope = new ReferencedEnvelope(5.0, 16, 47.0, 55.0, DefaultGeographicCRS.WGS84);

    public MapFrameTake2() {
        this.getContentPane().setLayout(new java.awt.BorderLayout());
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
        map.addLayer(Bullseye.LAYER);
        addGrids(map);

        this.setTitle("Frederick - Einsatz Karte");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        
        JMapPane mapPane = new JMapPane(map);
        mapPane.setBackground(Color.WHITE);
        mapPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mapPane.addMouseListener(new ScrollTool(mapPane));
        mapPane.addMouseListener(new PanTool(mapPane));
        mapPane.addMouseListener(new ContextMenuTool(mapPane));
        this.add(mapPane, BorderLayout.CENTER);

        MapLayerTable mapLayerTable = new MapLayerTable(mapPane);
        mapLayerTable.setPreferredSize(new Dimension(200, -1));
        //JSplitPane splitPane =
        //        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, mapLayerTable, mapPane);
        //this.add(splitPane, "grow");

        this.add(new MapBrowserPanel(mapPane), BorderLayout.LINE_START);
        this.add(new MapToolbar(mapPane), BorderLayout.PAGE_START);
        this.add(new StatusBar(), BorderLayout.PAGE_END);
        //this.setMapContent(map);
        //this.enableStatusBar(true);
        //this.enableToolBar(true);
        //this.enableLayerTable(true);
        //this.initComponents();
        this.setSize(1024, 768);
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
