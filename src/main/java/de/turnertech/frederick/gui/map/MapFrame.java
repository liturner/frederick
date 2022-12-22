package de.turnertech.frederick.gui.map;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.swing.JMapFrame;
import org.geotools.tile.TileService;
import org.geotools.tile.impl.osm.OSMService;
import org.geotools.tile.util.TileLayer;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import de.turnertech.frederick.Logging;
import de.turnertech.frederick.Resources;

public class MapFrame extends JMapFrame {
    
    public MapFrame() {
        MapContent map = new MapContent();
        String baseURL = "http://tile.openstreetmap.org/";
        TileService service = new OSMService("OSM", baseURL);
        TileLayer layer = new TileLayer(service);
        map.addLayer(layer);
        
        try {
            CoordinateReferenceSystem crs = CRS.decode("EPSG:3857");
            map.getViewport().setCoordinateReferenceSystem(crs);
            ReferencedEnvelope germanyEnvelope = new ReferencedEnvelope(581339, 1720274, 5950324, 7449462, crs);
            map.getViewport().setBounds(germanyEnvelope);
        } catch (FactoryException e) {
            Logging.LOGGER.severe("Could not decode CRS");
        }

        this.setTitle("Frederick - Einsatz Karte");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        this.setMapContent(map);
        this.enableStatusBar(true);
        this.enableToolBar(true);
        this.initComponents();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

}
