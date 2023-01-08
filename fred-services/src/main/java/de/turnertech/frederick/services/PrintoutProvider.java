package de.turnertech.frederick.services;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import de.turnertech.frederick.data.Deployment;

public interface PrintoutProvider {

    static List<PrintoutProvider> getInstances() {
        ServiceLoader<PrintoutProvider> services = ServiceLoader.load(PrintoutProvider.class);
        List<PrintoutProvider> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    /**
     * The name of the printout, as it will be shown to the User. E.g. "Fb Fü 2 - Einsatztagebuch"
     * 
     * @return the name, in UTF-8
     */
    public String getPrintoutName();
    
    /**
     * A document ready for print. All of the paper selection etc. will have been handled prior
     * to this call.
     * 
     * @return the document to print
     */
    public Pageable getPrintout(Deployment deployment, PageFormat pageFormat);

}
