package turnertech.frederick.gui.tray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import turnertech.frederick.Application;

public class FrederickTrayIconActionListener implements ActionListener {

    public static final String EXIT_COMMAND = "EXIT";

    public static final String SHOW_ETB_COMMAND = "SHOW_ETB";

    @Override
    public void actionPerformed(ActionEvent e) {

        if(EXIT_COMMAND.equals(e.getActionCommand())) {
            Application.exit();  
        } else if (SHOW_ETB_COMMAND.equals(e.getActionCommand())) {
            Application.showETB();
        }              
    }
}
