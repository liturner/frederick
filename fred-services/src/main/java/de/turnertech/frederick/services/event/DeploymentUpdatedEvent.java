package de.turnertech.frederick.services.event;

import java.awt.event.ActionEvent;

public class DeploymentUpdatedEvent extends ActionEvent {
    
    public static final String COMMAND = "DEPLOYMENT_UPDATED";

    public static final int getEventId() {
        return COMMAND.hashCode();
    }

    public DeploymentUpdatedEvent(final Object source) {
        super(source, DeploymentUpdatedEvent.getEventId(), COMMAND);
    }

}
