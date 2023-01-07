package de.turnertech.frederick.gui.deployment;

import javax.swing.JFrame;

import de.turnertech.frederick.services.FrameProvider;

public class DeploymentFrameProvider implements FrameProvider {
    
    private static JFrame instance;

    @Override
    public JFrame getFrame() {
        if(instance == null) {
            instance = new DeploymentFrame();
        }
        return instance;
    }

    @Override
    public String getShowFrameActionCommand() {
        return "SHOW_DEPLOYMENTS_ACTION_COMMAND";
    }

    @Override
    public String getFrameName() {
        return "Deployment Manager";
    }

}
