package de.turnertech.frederick.printout.common;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import de.turnertech.frederick.data.Deployment;
import de.turnertech.frederick.data.EtbEntry;
import de.turnertech.frederick.services.Logging;

public class DeploymentPrintout implements Pageable {

    private final PageFormat pageFormat;

    private final ArrayList<EtbPage> pages = new ArrayList<>();

    /**
     * Convenience conversion for printing sizes.
     * 
     * @param milimeters
     * @return the argument convertes to 1/72s of an inch
     */
    public static double mm(double milimeters) {
        // 25.4 mm in an inch, and the return value
        return milimeters * (72 / 25.4);
    }

    private class EtbPage implements Printable {

        private Paper paper;

        private Canvas canvas;

        EtbPage(Paper paper) {
            this.paper = paper;
            canvas = new Canvas();
            paper.getHeight();
            canvas.setSize(300, 300);
            canvas.setBackground(Color.BLUE);
            canvas.setVisible(true);
            canvas.setFocusable(false);
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            
            // User (0,0) is typically outside the
            // imageable area, so we must translate
            // by the X and Y values in the PageFormat
            // to avoid clipping.
            Graphics2D g2d = (Graphics2D)graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            //setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            //add(new Label("Hi There!"));
            //add(new Label("Another Label"));

            // TODO: We need to make our own with no insets....
            Border basicBorder = BorderFactory.createLineBorder(Color.BLACK);

            JPanel etbHeader = new JPanel();
            etbHeader.setBounds(0, 0, (int)pageFormat.getImageableWidth(), (int)mm(25));

            JPanel headerPanel = new JPanel();
            headerPanel.setBounds(0, 0, (int)pageFormat.getImageableWidth(), (int)mm(10));
            headerPanel.setBackground(Color.BLUE);
            headerPanel.setBorder(basicBorder);

            JPanel deploymentPanel = new JPanel();
            deploymentPanel.setBounds(0, (int)mm(15), (int)pageFormat.getImageableWidth(), (int)mm(10));
            deploymentPanel.setBackground(Color.GREEN);
            deploymentPanel.setBorder(basicBorder);

            JPanel docIdPanel = new JPanel();  
            docIdPanel.setBounds(0, 0, (int)mm(23), (int)headerPanel.getBounds().getHeight());
            docIdPanel.setBackground(Color.WHITE);
            docIdPanel.setBorder(basicBorder);

            JLabel label = new JLabel("Fb Fü 2");
            label.setBounds(docIdPanel.getBounds());
            label.setForeground(Color.BLACK);
            label.setBackground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            docIdPanel.add(label);

            headerPanel.add(docIdPanel);

            etbHeader.add(headerPanel);
            etbHeader.add(deploymentPanel);

            etbHeader.printAll(g2d);


            //graphics.drawString("Hello world!", 100, 100);
            
            graphics.drawString("Hello world!", 300, 300);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(200, 200, 200, 200);

            graphics.dispose();
            return PAGE_EXISTS;
        }

        /**
         * 
         * @param etbEntry
         * @param force If forced, this function will return true, even if the content did not fit on the page.
         * @return
         */
        public boolean addEtbEntry(EtbEntry etbEntry, boolean force) {
            return true;
        }

    }

    public DeploymentPrintout(Deployment deployment, PageFormat pageFormat) {
        Objects.requireNonNull(deployment, "Null Deployment passed to print job!");
        Objects.requireNonNull(pageFormat, "Null Page Format passed to print job!");
        
        this.pageFormat = pageFormat;

        // There is always one page, even if empty.
        EtbPage etbPage = new EtbPage(pageFormat.getPaper());
        pages.add(etbPage);

        List<EtbEntry> entries = deployment.getEtbEntries();
        for(int i = 0; i < entries.size(); ++i) {
            if(!etbPage.addEtbEntry(entries.get(i), false)) {
                etbPage = new EtbPage(pageFormat.getPaper());
                pages.add(etbPage);
                etbPage.addEtbEntry(entries.get(i), true);
            }
        }

    }

    /*
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // We have only one page, and 'page'
        // is zero-based
        if (pageIndex > 3) {
            return NO_SUCH_PAGE;
        }

        // User (0,0) is typically outside the
        // imageable area, so we must translate
        // by the X and Y values in the PageFormat
        // to avoid clipping.
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Now we perform our rendering
        graphics.drawString("Hello world!", 100, 100);

        // tell the caller that this page is part
        // of the printed document
        return PAGE_EXISTS;
    } */

    @Override
    public int getNumberOfPages() {
        Logging.LOGGER.info("Print: getNumberOfPages() : " + String.valueOf(pages.size()));
        return pages.size();
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        Logging.LOGGER.info("Print: getPageFormat()");
        return this.pageFormat;
    }

    @Override
    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        Logging.LOGGER.info("Print: getPrintable(" + String.valueOf(pageIndex) + ")");
        return pages.get(pageIndex);
    }
    
}
