package de.turnertech.frederick.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.swing.JFrame;

public interface FrameProvider {
    
    static List<FrameProvider> getInstances() {
        ServiceLoader<FrameProvider> services = ServiceLoader.load(FrameProvider.class);
        List<FrameProvider> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    public JFrame getFrame();

    public String getFrameName();

    public String getShowFrameActionCommand();
    
}
