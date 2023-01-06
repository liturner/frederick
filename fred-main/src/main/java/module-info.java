module de.turnertech.frederick {
    exports de.turnertech.frederick.main;

    opens de.turnertech.frederick.data to java.xml.bind;

    requires de.turnertech.tz.swing;

    requires java.logging;
    requires java.desktop;
    requires java.datatransfer;
    requires java.sql;

    requires java.xml.bind;

    requires org.geotools.opengis;
    requires org.geotools.referencing;
    requires org.geotools.coverage;
    requires org.geotools.main;
    requires org.geotools.render;
    requires org.geotools.epsg_hsql;
    requires org.geotools.grid;
    requires org.geotools.tile_client;
    requires org.geotools.swing;
    requires org.geotools.text.cql;
    requires org.geotools.http;
    requires org.geotools.metadata;

    requires org.locationtech.jts;

    // Will later be transitive in GeoTool
    requires systems.uom.common;
    requires aircompressor;
    requires org.checkerframework.checker.qual;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires commons.pool;
    requires org.apache.commons.text;
    requires disruptor;
    requires ejml.core;
    requires ejml.ddense;
    requires com.google.errorprone.annotations;
    requires failureaccess;
    requires com.sun.xml.fastinfoset;
    requires GeographicLib.Java;
    requires com.google.common;
    requires hsqldb;
    requires imageio.ext.geocore;
    requires imageio.ext.streams;
    requires imageio.ext.tiff;
    requires imageio.ext.utilities;
    requires tech.units.indriya;
    requires com.sun.istack.runtime;
    requires j2objc.annotations;
    requires com.fasterxml.jackson.core;
    requires jai.codec;
    requires jai.core;
    requires jai.imageio;
    requires java.annotation;
    requires java.activation;
    requires javax.inject;
    requires com.sun.xml.bind;
    requires jgridshift.core;
    requires jt.affine;
    requires jt.algebra;
    requires jt.bandcombine;
    requires jt.bandmerge;
    requires jt.bandselect;
    requires jt.binarize;
    requires jt.border;
    requires jt.buffer;
    requires jt.classifier;
    requires jt.colorconvert;
    requires jt.colorindexer;
    requires jt.crop;
    requires jt.errordiffusion;
    requires jt.format;
    requires jt.imagefunction;
    requires jt.iterators;
    requires jt.lookup;
    requires jt.mosaic;
    requires jt.nullop;
    requires jt.orderdither;
    requires jt.piecewise;
    requires jt.rescale;
    requires jt.rlookup;
    requires jt.scale;
    requires jt.scale2;
    requires jt.shadedrelief;
    requires jt.stats;
    requires jt.translate;
    requires jt.utilities;
    requires jt.utils;
    requires jt.vectorbin;
    requires jt.warp;
    requires jt.zonal;
    requires jt.zonalstats;
    requires listenablefuture;
    requires miglayout;
    requires org.geotools.ogc.net.opengis.ows;
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.ecore.xmi;
    requires org.geotools.ogc.org.w3.xlink;
    requires re2j;
    requires slf4j.api;
    requires org.jvnet.staxex;
    requires com.sun.xml.txw2;
    requires java.measure;
}
