package de.turnertech.frederick.services.event;

import java.awt.event.ActionEvent;

public class DeploymentOpenedEvent extends ActionEvent {
    
    public static final String COMMAND = "DEPLOYMENT_OPENED";

    public static final int getEventId() {
        return COMMAND.hashCode();
    }

    public DeploymentOpenedEvent(final Object source) {
        super(source, DeploymentOpenedEvent.getEventId(), COMMAND);
    }

}
