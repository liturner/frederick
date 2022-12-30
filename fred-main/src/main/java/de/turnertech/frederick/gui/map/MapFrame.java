package de.turnertech.frederick.gui.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.DirectPosition2D;
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
import org.geotools.tile.TileService;
import org.geotools.tile.impl.osm.OSMService;
import org.geotools.tile.util.TileLayer;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import de.turnertech.frederick.data.Bullseye;
import de.turnertech.frederick.gui.map.feature.BullseyeLayer;
import de.turnertech.frederick.gui.map.tool.ContextMenuTool;
import de.turnertech.frederick.gui.map.tool.PanTool;
import de.turnertech.frederick.gui.map.tool.ScrollTool;
import de.turnertech.frederick.gui.status.StatusBar;
import de.turnertech.frederick.main.Application;
import de.turnertech.frederick.main.Database;
import de.turnertech.frederick.main.Logging;
import de.turnertech.frederick.main.Resources;

public class MapFrame extends JFrame implements ActionListener {
    
    private final ReferencedEnvelope germanyEnvelope = new ReferencedEnvelope(5.0, 16, 47.0, 55.0, DefaultGeographicCRS.WGS84);

    private final MapToolbar mapToolbar;

    public MapFrame() {
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        Application.getDatabase().addActionListener(this);
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
        map.addLayer(BullseyeLayer.instance());
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

        mapToolbar = new MapToolbar(mapPane);

        this.add(new MapBrowserPanel(mapPane), BorderLayout.LINE_START);
        this.add(mapToolbar, BorderLayout.PAGE_START);
        this.add(new StatusBar(), BorderLayout.PAGE_END);
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getID() == Database.DEPLOYMENT_UPDATED_EVENT_ID || actionEvent.getID() == Database.DEPLOYMENT_OPENED_EVENT_ID) {
            if(Application.getService().getBullseye().isPresent()) {
                Bullseye bullsFromStorage = Application.getService().getBullseye().get();
                BullseyeLayer.instance().set(new DirectPosition2D(DefaultGeographicCRS.WGS84, bullsFromStorage.getX(), bullsFromStorage.getY()));
                mapToolbar.setFocusBullseyeActionEnabled(true);
            } else {
                BullseyeLayer.instance().clear();
                mapToolbar.setFocusBullseyeActionEnabled(false);
            }       
        } else if(actionEvent.getID() == Database.DEPLOYMENT_CLOSED_EVENT_ID) {
            BullseyeLayer.instance().clear();
            mapToolbar.setFocusBullseyeActionEnabled(false);
        }
    }
}
