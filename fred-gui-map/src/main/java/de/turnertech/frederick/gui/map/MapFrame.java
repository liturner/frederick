package de.turnertech.frederick.gui.map;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
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
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
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
import de.turnertech.frederick.data.TacticalElement;
import de.turnertech.frederick.gui.common.StatusBar;
import de.turnertech.frederick.gui.map.action.AddTacticalSymbolAction;
import de.turnertech.frederick.gui.map.feature.BullseyeLayer;
import de.turnertech.frederick.gui.map.feature.TacticalSymbolLayer;
import de.turnertech.frederick.gui.map.tool.ContextMenuTool;
import de.turnertech.frederick.gui.map.tool.PanTool;
import de.turnertech.frederick.gui.map.tool.ScrollTool;
import de.turnertech.frederick.services.ActionService;
import de.turnertech.frederick.services.Logging;
import de.turnertech.frederick.services.PersistanceProvider;
import de.turnertech.frederick.services.Resources;
import de.turnertech.frederick.services.event.DeploymentClosedEvent;
import de.turnertech.frederick.services.event.DeploymentOpenedEvent;
import de.turnertech.frederick.services.event.DeploymentUpdatedEvent;
import de.turnertech.tz.swing.TacticalSymbol;

public class MapFrame extends JFrame implements ActionListener, DropTargetListener {
    
    private final ReferencedEnvelope germanyEnvelope = new ReferencedEnvelope(5.0, 16, 47.0, 55.0, DefaultGeographicCRS.WGS84);

    private final MapToolbar mapToolbar;

    private final JMapPane mapPane;

    public MapFrame() {
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        ActionService.addActionListener(this);
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
        map.addLayer(TacticalSymbolLayer.instance());
        addGrids(map);

        this.setTitle("Frederick - Einsatz Karte");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        
        GTRenderer renderer = new StreamingRenderer();

        mapPane = new JMapPane(map);
        mapPane.setRenderer(renderer);
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

        mapPane.setDropTarget(new DropTarget(mapPane, this));
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
        if(actionEvent.getID() == DeploymentUpdatedEvent.getEventId() || actionEvent.getID() == DeploymentOpenedEvent.getEventId()) {
            if(PersistanceProvider.getInstance().getCurrentDeployment().getBullseye() != null) {
                Bullseye bullsFromStorage = PersistanceProvider.getInstance().getCurrentDeployment().getBullseye();
                BullseyeLayer.instance().set(new DirectPosition2D(DefaultGeographicCRS.WGS84, bullsFromStorage.getX(), bullsFromStorage.getY()));
                mapToolbar.setFocusBullseyeActionEnabled(true);
            } else {
                BullseyeLayer.instance().clear();
                mapToolbar.setFocusBullseyeActionEnabled(false);
            }   
            
            Collection<TacticalElement> allElements = PersistanceProvider.getInstance().getCurrentDeployment().getTacticalSymbolEntries();
            TacticalSymbolLayer.instance().clear();
            for(TacticalElement element : allElements) {
                TacticalSymbolLayer.instance().add(element);
            }
            
        } else if(actionEvent.getID() == DeploymentClosedEvent.getEventId()) {
            BullseyeLayer.instance().clear();
            mapToolbar.setFocusBullseyeActionEnabled(false);
        }
    }





    @Override
    public void dragEnter(DropTargetDragEvent event) {
        if(!event.isDataFlavorSupported(TacticalSymbol.DATA_FLAVOR)) {
            event.rejectDrag();            
            return;
        }

    }

    @Override
    public void dragExit(DropTargetEvent event) {
        // According to docs, nothing mandatory to do here
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
        if(!event.isDataFlavorSupported(TacticalSymbol.DATA_FLAVOR)) {
            event.rejectDrag();            
            return;
        }

    }

    @Override
    public void drop(DropTargetDropEvent event) {
        // Check if supported flavour, get the transferrable etc.
        if(!event.isDataFlavorSupported(TacticalSymbol.DATA_FLAVOR)) {
            event.rejectDrop();            
            return;
        }

        // Must be called BEFORE accessing the Transferable! https://docs.oracle.com/javase/10/docs/api/java/awt/dnd/DropTargetListener.html#acceptDrag(int)
        event.acceptDrop(DnDConstants.ACTION_COPY);
        TacticalSymbol data;

        try {
            data = (TacticalSymbol)event.getTransferable().getTransferData(TacticalSymbol.DATA_FLAVOR);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
            event.dropComplete(false);
            return;
        }

        Point screenPoint = event.getLocation();
        AddTacticalSymbolAction action = new AddTacticalSymbolAction(mapPane, screenPoint.getX(), screenPoint.getY(), data);
        action.actionPerformed(null);

        event.dropComplete(true);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
        // According to docs, nothing mandatory to do here
    }
}
