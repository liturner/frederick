package de.turnertech.frederick.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.turnertech.frederick.services.Printing;
import de.turnertech.frederick.services.PrintoutProvider;

public class FrederickTrayIconPrintActionListener implements ActionListener {

    public static final String PRINT_COMMAND = "PRINT";

    private final PrintoutProvider printoutProvider;

    FrederickTrayIconPrintActionListener(PrintoutProvider printoutProvider) {
        this.printoutProvider = printoutProvider;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(PRINT_COMMAND.equals(e.getActionCommand())) {
            Printing.print(printoutProvider);
        }
    }
}
