module de.turnertech.frederick {
    exports de.turnertech.frederick;

    opens de.turnertech.frederick.data to jakarta.xml.bind;

    requires de.turnertech.tz.swing;

    requires java.logging;
    requires java.desktop;
    requires java.sql;
    requires jakarta.xml.bind;
    
    requires org.geotools.opengis;
    requires org.geotools.referencing;
    requires org.geotools.coverage;
    requires org.geotools.main;
    requires org.geotools.render;
    requires org.geotools.epsg_hsql;
    requires org.geotools.shapefile;
    requires org.geotools.grid;
    requires org.geotools.tile_client;
    requires org.geotools.swing;
    requires org.geotools.text.cql;
    requires org.geotools.http;
    requires org.geotools.metadata;

    requires org.locationtech.jts;
}
