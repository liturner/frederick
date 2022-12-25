package de.turnertech.frederick.gui.map.feature;

import java.awt.Color;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.AnchorPoint;
import org.geotools.styling.Displacement;
import org.geotools.styling.Fill;
import org.geotools.styling.Font;
import org.geotools.styling.Graphic;
import org.geotools.styling.LabelPlacement;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.TextSymbolizer;
import org.geotools.swing.MapPane;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import de.turnertech.frederick.Application;
import de.turnertech.frederick.Database;
import de.turnertech.frederick.Logging;
import de.turnertech.frederick.data.EtbEntry;
import de.turnertech.frederick.gui.map.MapHelper;

public class Bullseye {
    
    private static final Style STYLE;

    private static final SimpleFeatureType TYPE;

    private static final ListFeatureCollection COLLECTION;

    private static final CollectionFeatureSource SOURCE;

    public static final BullseyeLayer LAYER;

    private static final String GEOMETRY = "geom";

    private static StyleFactory sf = CommonFactoryFinder.getStyleFactory(null);

    private static FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

    static {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Bullseye");
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.add(GEOMETRY, Point.class);
        TYPE = builder.buildFeatureType();

        STYLE = createStyle();

        COLLECTION = new ListFeatureCollection(TYPE);

        SOURCE = new CollectionFeatureSource(COLLECTION);

        LAYER = new BullseyeLayer(SOURCE, STYLE);
        LAYER.addMapLayerListener(null);
    }

    public static Style createStyle() {
        String wellKnownName = "Circle";
        Color lineColor = Color.RED;
        Color fillColor = Color.BLUE;
        float opacity = 1.0f;
        float size = 15;
        String labelField = null;
        Font labelFont = null;
        
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

        if (labelField == null) {
            return SLD.wrapSymbolizers(pointSym);

        } else {
            Font font = (labelFont == null ? sf.getDefaultFont() : labelFont);
            Fill labelFill = sf.createFill(ff.literal(Color.BLACK));
            AnchorPoint anchor = sf.createAnchorPoint(ff.literal(0.5), ff.literal(0.0));
            Displacement disp = sf.createDisplacement(ff.literal(0), ff.literal(5));
            LabelPlacement placement = sf.createPointPlacement(anchor, disp, ff.literal(0));

            TextSymbolizer textSym =
                    sf.createTextSymbolizer(
                            labelFill,
                            new Font[] {font},
                            null,
                            ff.property(labelField),
                            placement,
                            null);

            return SLD.wrapSymbolizers(pointSym, textSym);
        }
    }

    public static void clear() {
        COLLECTION.clear();
    }

    public static void set(DirectPosition2D position) {
        clear();

        MathTransform transform;
        DirectPosition2D crs84Position;
        try {
            transform = CRS.findMathTransform(position.getCoordinateReferenceSystem(), TYPE.getCoordinateReferenceSystem(), true);
            crs84Position = new DirectPosition2D();
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

        // ToDo - Consider centralising this in Application? Maybe using events? Maybe this is fine as it is?
        EtbEntry etbEntry = new EtbEntry(Date.from(Instant.now()), Application.CURRENT_USER, "Einsatzort festgelegt als " + MapHelper.format(crs84Position));
        Application.getDatabase().getCurrentDeployment().getEtbEntries().add(etbEntry);
        Application.getDatabase().notifyActionListeners(Database.DEPLOYMENT_UPDATED_EVENT);
        
        LAYER.makeThisFunctionBetterLater();
    }

    public static Optional<DirectPosition2D> getPosition() {
        if(!COLLECTION.isEmpty()) {
            for(SimpleFeature feature : COLLECTION) {
                Point point = (Point)feature.getAttribute(GEOMETRY);
                if(point != null) {
                    return Optional.of(new DirectPosition2D(TYPE.getCoordinateReferenceSystem(), point.getX(), point.getY()));
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<ReferencedEnvelope> getArea(MapPane mapPane) {
        Optional<DirectPosition2D> positionOptional = getPosition();
        if(positionOptional.isEmpty()) {
            return Optional.empty();
        }

        try {
            DirectPosition2D position = positionOptional.get();
            ReferencedEnvelope currentEnvelope = mapPane.getDisplayArea().transform(TYPE.getCoordinateReferenceSystem(), true);
            DirectPosition2D lowerCorner = new DirectPosition2D(currentEnvelope.getLowerCorner());

            double ratio = (position.getX() - lowerCorner.getX()) / (position.getY() - lowerCorner.getY());
            double height = 0.01;
            double width = 0.01;
            DirectPosition2D newLowerCorner = new DirectPosition2D(position.getX() - width, position.getY() - height);

            //ReferencedEnvelope newMapArea = new ReferencedEnvelope(mapPane.getDisplayArea().getCoordinateReferenceSystem());
            Envelope2D newMapArea = new Envelope2D();
            newMapArea.setFrameFromCenter(position, newLowerCorner);

            ReferencedEnvelope newReferencedArea = new ReferencedEnvelope(TYPE.getCoordinateReferenceSystem());
            newReferencedArea.expandToInclude(newMapArea.getLowerCorner());
            newReferencedArea.expandToInclude(newMapArea.getUpperCorner());
            ReferencedEnvelope returnObject = newReferencedArea.transform(mapPane.getDisplayArea().getCoordinateReferenceSystem(), true);

            return Optional.of(returnObject);
        } catch (TransformException | FactoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Optional.empty();
        }
        
        /**
         * new Envelope2D(
            position.getX() - width * 0.5,
            position.getX() + width * 0.5,
            position.getY() - 0.5,
            position.getY() + 0.5
        )
         */
    }

    private Bullseye() {

    }

}
