package de.turnertech.frederick.main;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import de.turnertech.frederick.printing.DeploymentPrintout;

public class Printing {
    
    public static void initialise() {

    }

    public static void printDeployment() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new DeploymentPrintout());
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                // The job did not successfully
                // complete
            }
        }
    }

}
