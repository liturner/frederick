package de.turnertech.frederick.services.event;

import java.awt.event.ActionEvent;

public class DeploymentDeletedEvent extends ActionEvent {
    
    public static final String COMMAND = "DEPLOYMENT_DELETED";

    public static final int getEventId() {
        return COMMAND.hashCode();
    }

    public DeploymentDeletedEvent(final Object source) {
        super(source, DeploymentDeletedEvent.getEventId(), COMMAND);
    }

}
