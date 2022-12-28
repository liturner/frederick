module de.turnertech.frederick {
    exports de.turnertech.frederick;

    requires java.logging;
    requires java.desktop;
    requires jakarta.xml.bind;

    requires org.geotools.main;
    requires org.geotools.swing;
    requires org.geotools.epsg_hsql;
    requires org.geotools.grid;
    requires org.geotools.tile_client;
    requires org.geotools.referencing;
    requires org.geotools.render;
    requires org.geotools.shapefile;
    requires org.geotools.opengis;
    requires org.geotools.coverage;
    requires org.geotools.referencing.crs;
}
