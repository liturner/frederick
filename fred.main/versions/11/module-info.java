module fred.main {
    requires java.datatransfer;
    requires java.xml.bind;
    requires org.geotools.grid;
    requires org.geotools.opengis;
    requires org.geotools.tile_client;
    requires org.locationtech.jts;

    requires transitive de.turnertech.tz.swing;
    requires transitive java.desktop;
    requires transitive java.logging;
    requires transitive org.geotools.main;
    requires transitive org.geotools.metadata;
    requires transitive org.geotools.referencing;
    requires transitive org.geotools.render;
    requires transitive org.geotools.swing;

    exports de.turnertech.frederick.data;
    exports de.turnertech.frederick.gui;
    exports de.turnertech.frederick.gui.deployments;
    exports de.turnertech.frederick.gui.etb;
    exports de.turnertech.frederick.gui.map;
    exports de.turnertech.frederick.gui.map.action;
    exports de.turnertech.frederick.gui.map.browser;
    exports de.turnertech.frederick.gui.map.feature;
    exports de.turnertech.frederick.gui.map.symbols;
    exports de.turnertech.frederick.gui.map.tool;
    exports de.turnertech.frederick.gui.status;
    exports de.turnertech.frederick.gui.tray;
    exports de.turnertech.frederick.main;
    exports de.turnertech.frederick.printing;

}
