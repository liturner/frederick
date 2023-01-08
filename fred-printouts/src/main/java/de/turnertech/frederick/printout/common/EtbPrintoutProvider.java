package de.turnertech.frederick.printout.common;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;

import de.turnertech.frederick.data.Deployment;
import de.turnertech.frederick.services.PrintoutProvider;

public class EtbPrintoutProvider implements PrintoutProvider {

    @Override
    public String getPrintoutName() {
        return "Fb Fü 2 - Einsatztagebuch";
    }

    @Override
    public Pageable getPrintout(Deployment deployment, PageFormat pageFormat) {
        return new DeploymentPrintout(deployment, pageFormat);
    }
    
}
