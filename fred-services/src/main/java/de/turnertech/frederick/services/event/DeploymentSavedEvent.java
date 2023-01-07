package de.turnertech.frederick.services.event;

import java.awt.event.ActionEvent;

public class DeploymentSavedEvent extends ActionEvent {
    
    public static final String COMMAND = "DEPLOYMENT_SAVED";

    public static final int getEventId() {
        return COMMAND.hashCode();
    }

    public DeploymentSavedEvent(final Object source) {
        super(source, DeploymentSavedEvent.getEventId(), COMMAND);
    }

}
