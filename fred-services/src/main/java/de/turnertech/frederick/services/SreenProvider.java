package de.turnertech.frederick.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface  SreenProvider {
    
    static List<SreenProvider> getInstances() {
        ServiceLoader<SreenProvider> services = ServiceLoader.load(SreenProvider.class);
        List<SreenProvider> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }
}
