package de.turnertech.frederick.gui.map.feature;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
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
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.opengis.style.GraphicalSymbol;

import de.turnertech.frederick.data.TacticalElement;
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

    private static final String SYMBOL = "sym";

    private static StyleFactory sf = CommonFactoryFinder.getStyleFactory(null);

    private static FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

    static {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Tactical Symbols");
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.add(GEOMETRY, Point.class);
        builder.add(SYMBOL, Integer.class);
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
        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        Collection<TacticalSymbol> tacticalSymbols = TacticalSymbolFactory.getTacticalSymbols();

        for(TacticalSymbol tacticalSymbol : tacticalSymbols) {
            List<GraphicalSymbol> symbols = new ArrayList<>();
            symbols.add(sf.createExternalGraphic(tacticalSymbol.getImageIcon(64, 64), "PNG"));
            Graphic graphic = sf.graphic(symbols, null, ff.literal(5), null, null, null);

            PointSymbolizer pointSym = sf.createPointSymbolizer(graphic, GEOMETRY);

            Rule rule = sf.createRule();
            Filter filter = ff.equals(ff.property(SYMBOL), ff.literal(tacticalSymbol.hashCode()));
            rule.setFilter(filter);
            rule.symbolizers().add(pointSym);

            fts.rules().add(rule);
        }

        // Default rule for fallback
        Rule rule = sf.createRule();
        rule.setElseFilter(true);
        Fill fill = sf.createFill(ff.literal(Color.RED), ff.literal(1.0));
        Stroke stroke = sf.createStroke(ff.literal(Color.RED), ff.literal(1.0));
        Mark mark = sf.getCircleMark();
        mark.setFill(fill);
        mark.setStroke(stroke);
        Graphic graphic = sf.createDefaultGraphic();
        graphic.graphicalSymbols().clear();
        graphic.graphicalSymbols().add(mark);
        graphic.setSize(ff.literal(10));
        rule.symbolizers().add(sf.createPointSymbolizer(graphic, GEOMETRY));
        fts.rules().add(rule);

        Style style = sf.createStyle();
        style.featureTypeStyles().add(fts);
        return style;
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

    public void add(final TacticalElement element) {
        DirectPosition2D position = new DirectPosition2D(DefaultGeographicCRS.WGS84, element.getX(), element.getY());
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
        featureBuilder.set(GEOMETRY, point);
        featureBuilder.set(SYMBOL, element.getIcon());
        COLLECTION.add(featureBuilder.buildFeature(null));
    }

}
