package de.turnertech.frederick.services;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Printing {
    
    private Printing() {
        // Static helper class
    }

    // https://docstore.mik.ua/orelly/java-ent/jfc/ch21_01.htm
    public static void print(PrintoutProvider printoutProvider) {
        
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(printoutProvider.getPrintoutName());

        PageFormat pageFormat = job.pageDialog(job.defaultPage());

        job.setPageable(printoutProvider.getPrintout(PersistanceProvider.getInstance().getCurrentDeployment(), pageFormat));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                Logging.LOGGER.severe("Could not print the document!");
            }
        }
    }

}
