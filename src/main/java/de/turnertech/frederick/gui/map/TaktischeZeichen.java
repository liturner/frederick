package de.turnertech.frederick.gui.map;

import java.awt.Image;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.ImageIcon;

import de.turnertech.frederick.Application;

public class TaktischeZeichen {
 
    final String path;

    private ImageIcon imageIcon;

    private ArrayList<AbstractMap.SimpleEntry<Integer, ImageIcon>> cache = new ArrayList<>(1);

    public TaktischeZeichen(final String path) {
        this.path = path;
    }
    
    public ImageIcon getImageIcon() {
        if(imageIcon == null) {
            imageIcon = new ImageIcon(Application.class.getResource(this.path));
        }
        return imageIcon;
    }

    public ImageIcon getImageIcon(final int w, final int h) {
        return this.getImageIcon(w, h, Image.SCALE_SMOOTH);
    }

    /**
     * public static final int SCALE_DEFAULT = 1;
     * public static final int SCALE_FAST = 2;
     * public static final int SCALE_SMOOTH = 4; 
     * public static final int SCALE_REPLICATE = 8;
     * public static final int SCALE_AREA_AVERAGING = 16;
     * 
     * @param w
     * @param h
     * @param method
     * @return
     */
    public ImageIcon getImageIcon(final int w, final int h, final int method) {
        // Check the cache before creating new.
        final int cacheKey = getCacheKey(w, h, method);
        Optional<ImageIcon> cacheItem = getCacheItem(cacheKey);
        if(cacheItem.isPresent()) {
            return cacheItem.get();
        }

        ImageIcon origional = getImageIcon();
        ImageIcon scaled = new ImageIcon(origional.getImage().getScaledInstance(h, w, method));
        this.cache.add(new SimpleEntry<>(cacheKey, scaled));
        return scaled;
    }

    /**
     * Fast method to approximate a unique key. This returns an integer with the
     * individual parameters shifted, allowinf space for all reasonable values.
     * 
     * e.g. (128, 128, 32) will return 1280128032 
     * 
     * @param w 
     * @param h
     * @param method
     * @return
     */
    private int getCacheKey(final int w, final int h, final int method) {
        return w * 1000000 + h * 100 + method;
    }

    private Optional<ImageIcon> getCacheItem(final int key) {
        for(SimpleEntry<Integer, ImageIcon> cacheItem : this.cache) {
            if(cacheItem.getKey().equals(key)) {
                return Optional.of(cacheItem.getValue());
            }
        }
        return Optional.empty();
    }

}
