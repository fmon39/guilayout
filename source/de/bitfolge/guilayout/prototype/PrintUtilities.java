package de.bitfolge.guilayout.prototype;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;

public class PrintUtilities implements Printable {

    public static PageFormat pageFormat=null;
    private Component componentToBePrinted;

    public static void printComponent(Component c) throws PrinterException {
        new PrintUtilities(c).print();
    }

    public PrintUtilities(Component componentToBePrinted) {
        this.componentToBePrinted = componentToBePrinted;
    }
    
    private static void defaultPageFormat() {
        if (pageFormat==null) {
            pageFormat = PrinterJob.getPrinterJob().defaultPage(); 
        }
    }
    
    public static void pageDialog() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        defaultPageFormat();
        pageFormat = printJob.pageDialog(pageFormat);
    }

    public void print() throws PrinterException {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        defaultPageFormat();
        printJob.setPrintable(this, pageFormat);
        if (printJob.printDialog()) printJob.print();
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat
                    .getImageableY());
            double scaleX = componentToBePrinted.getPreferredSize().getWidth()/pageFormat.getImageableWidth();
            double scaleY = componentToBePrinted.getPreferredSize().getHeight()+50/pageFormat.getImageableHeight();
            double scale = 0.8*Math.min(scaleX, scaleY);
            g2d.scale(scale, scale);
            disableDoubleBuffering(componentToBePrinted);
            componentToBePrinted.paint(g2d);
            enableDoubleBuffering(componentToBePrinted);
            return (PAGE_EXISTS);
        }
    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}