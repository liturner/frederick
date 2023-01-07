package de.turnertech.frederick.gui.map.feature;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapLayerEvent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Graphic;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.opengis.style.GraphicalSymbol;

import de.turnertech.frederick.services.Logging;
import de.turnertech.tz.swing.TacticalSymbol;
import de.turnertech.tz.swing.TacticalSymbolFactory;

public class TacticalSymbolLayer extends FeatureLayer {
    
    private static TacticalSymbolLayer instance;

    private static final Style STYLE;

    private static final SimpleFeatureType TYPE;

    private static final ListFeatureCollection COLLECTION;

    private static final CollectionFeatureSource SOURCE;

    private static final String GEOMETRY = "geom";

    private static StyleFactory sf = CommonFactoryFinder.getStyleFactory(null);

    private static FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

    static {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Tactical Symbols");
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.add(GEOMETRY, Point.class);
        TYPE = builder.buildFeatureType();

        STYLE = createStyle();

        COLLECTION = new ListFeatureCollection(TYPE);

        SOURCE = new CollectionFeatureSource(COLLECTION);
    }

    public static TacticalSymbolLayer instance() {
        if(instance == null) {
            instance = new TacticalSymbolLayer();
        }
        return instance;
    }

    private TacticalSymbolLayer() {
        super(SOURCE, STYLE, "Tactical Symbols");
    }

    @Override
    public void fireMapLayerListenerLayerChanged(int eventType) {
        super.fireMapLayerListenerLayerChanged(eventType);
    }

    // https://docs.geotools.org/stable/userguide/tutorial/map/style.html
    public static Style createStyle() {
        
        TacticalSymbol tz = TacticalSymbolFactory.getTacticalSymbols().iterator().next();
        
        List<GraphicalSymbol> symbols = new ArrayList<>();
        symbols.add(sf.createExternalGraphic(tz.getImageIcon(), "PNG"));
        Graphic graphic = sf.graphic(symbols, null, ff.literal(10), null, null, null);

        PointSymbolizer pointSym = sf.createPointSymbolizer(graphic, null);
        return SLD.wrapSymbolizers(pointSym);
    }

    /**
     * Clears the map and triggers a map layer event if there
     * was any data to remove.
     * 
     * @see TacticalSymbolLayer#clear(boolean)
     */
    public void clear() {
        clear(true);
    }

    /**
     * Clears the layers collection and sends a map layer event
     * based on the value of the notify parameter.
     * 
     * If the internal data collection was anyway empty then no
     * event will be triggered.
     * 
     * @param notify Send a map layer event or not?
     * @see TacticalSymbolLayer#clear()
     */
    private void clear(final boolean notify) {
        if(COLLECTION.isEmpty()) {
            return;
        }
        COLLECTION.clear();
        if(notify) {
            fireMapLayerListenerLayerChanged(MapLayerEvent.DATA_CHANGED);
        }
    }

    public void add(final DirectPosition2D position) {
        MathTransform transform;
        DirectPosition2D crs84Position;
        try {
            transform = CRS.findMathTransform(position.getCoordinateReferenceSystem(), TYPE.getCoordinateReferenceSystem(), true);
            crs84Position = new DirectPosition2D(DefaultGeographicCRS.WGS84);
            transform.transform(position, crs84Position);
        } catch (FactoryException e2) {
            Logging.LOGGER.severe("Could not create CRS transform. Coordinates may be false!");
            return;
        } catch (MismatchedDimensionException | TransformException e1) {
            Logging.LOGGER.severe("Could not transform. Coordinates may be false!");
            return;
        }
        
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(crs84Position.getX(), crs84Position.getY()));
        featureBuilder.set("geom", point);
        COLLECTION.add(featureBuilder.buildFeature(null));
    }

}
