package de.turnertech.frederick.services.event;

import java.awt.event.ActionEvent;

public class DeploymentClosedEvent extends ActionEvent {
    
    public static final String COMMAND = "DEPLOYMENT_CLOSED";

    public static final int getEventId() {
        return COMMAND.hashCode();
    }

    public DeploymentClosedEvent(final Object source) {
        super(source, DeploymentClosedEvent.getEventId(), COMMAND);
    }

}
