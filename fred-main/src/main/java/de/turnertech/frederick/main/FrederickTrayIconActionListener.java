package de.turnertech.frederick.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import de.turnertech.frederick.services.FrameProvider;

public class FrederickTrayIconActionListener implements ActionListener {

    public static final String EXIT_COMMAND = "EXIT";

    public static final String SHOW_MAP_COMMAND = "SHOW_MAP";

    @Override
    public void actionPerformed(ActionEvent e) {

        if(EXIT_COMMAND.equals(e.getActionCommand())) {
            System.exit(-1);
        }

        List<FrameProvider> frameProviders = FrameProvider.getInstances();
        for(FrameProvider frameProvider : frameProviders) {
            if(frameProvider.getShowFrameActionCommand().equals(e.getActionCommand())) {
                frameProvider.getFrame().setVisible(true);
            }
        }
    }
}
