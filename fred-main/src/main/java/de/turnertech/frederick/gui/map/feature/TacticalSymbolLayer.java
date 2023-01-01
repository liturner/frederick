package de.turnertech.frederick.gui.map.feature;

import java.awt.Color;

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
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
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

import de.turnertech.frederick.main.Logging;

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

    public static Style createStyle() {
        String wellKnownName = "Circle";
        Color lineColor = Color.RED;
        Color fillColor = Color.BLUE;
        float opacity = 1.0f;
        float size = 15;
        
        StyleBuilder builder = new StyleBuilder();
        Stroke stroke = sf.createStroke(ff.literal(lineColor), ff.literal(1.0f));
        Fill fill = Fill.NULL;
        if (fillColor != null) {
            fill = sf.createFill(ff.literal(fillColor), ff.literal(opacity));
        }

        Mark mark = sf.createMark(
            ff.literal(wellKnownName), stroke, fill, ff.literal(size), ff.literal(0)
        );

        Mark outerRing = sf.createMark(
            ff.literal("Circle"), stroke, Fill.NULL, ff.literal(30.0f), ff.literal(0)
        );
        Graphic graphic = sf.createDefaultGraphic();
        graphic.graphicalSymbols().clear();
        graphic.graphicalSymbols().add(outerRing);
        graphic.graphicalSymbols().add(mark);
        graphic.setSize(ff.literal(30.0f));
        
        graphic = builder.createGraphic(null, new Mark[]{outerRing, mark}, null, 1, 30.0, 0.0);

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
        fireMapLayerListenerLayerChanged(MapLayerEvent.DATA_CHANGED);
    }

}
