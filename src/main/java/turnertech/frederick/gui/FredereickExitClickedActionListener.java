package turnertech.frederick.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import turnertech.frederick.Application;

public class FredereickExitClickedActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Application.exit();        
    }
    
}
