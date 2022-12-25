package de.turnertech.frederick.gui.map.feature;

import java.awt.Color;

import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class Bullseye {
    
    public static final Style STYLE;

    public static final SimpleFeatureType TYPE;

    public static final ListFeatureCollection COLLECTION;

    public static final Layer LAYER;

    public static final String GEOMETRY = "geom";

    static {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Bullseye");
        builder.setCRS(DefaultGeographicCRS.WGS84);
        builder.add("geom", Point.class);
        TYPE = builder.buildFeatureType();

        STYLE = SLD.createPointStyle("Circle", Color.RED, Color.BLUE, 1.0f, 15);

        COLLECTION = new ListFeatureCollection(TYPE);

        LAYER = new FeatureLayer(COLLECTION, STYLE);
    }

    public static SimpleFeature create(final double x, final double y) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(x, y));
        featureBuilder.set("geom", point);
        return featureBuilder.buildFeature(null);
    }

    private Bullseye() {

    }

}
