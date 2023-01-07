package de.turnertech.frederick.main;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import de.turnertech.frederick.data.Bullseye;
import de.turnertech.frederick.data.EtbEntry;
import de.turnertech.frederick.data.TacticalElement;
import de.turnertech.frederick.services.ActionService;
import de.turnertech.frederick.services.ApplicationService;
import de.turnertech.frederick.services.PersistanceProvider;
import de.turnertech.frederick.services.event.DeploymentUpdatedEvent;

/**
 * This is the service layer for the application. All actions performed by the GUI
 * should come through here. Consider it the API for the application.
 * 
 * This class should not have any dependency on Geotools, or the GUI in general.
 */
public class Service extends ApplicationService {
    
    /**
     * Sets the current deployments Bullseye position.
     * 
     * @param bullseye Setting to null will clear the bullseye.
     * @param logPosition The bullseye coordinates as they should be entered into the ETB.
     */
    public void setBullseye(final Bullseye bullseye, final String logPosition) {
        PersistanceProvider.getInstance().getCurrentDeployment().setBullseye(bullseye);
        EtbEntry etbEntry = new EtbEntry(Date.from(Instant.now()), ApplicationService.CURRENT_USER, "Einsatzort festgelegt als " + logPosition);
        PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().add(etbEntry);
        ActionService.notifyActionListeners(new DeploymentUpdatedEvent(this));
        PersistanceProvider.getInstance().saveCurrentDeployment();
    }

    public Optional<Bullseye> getBullseye() {
        return Optional.ofNullable(PersistanceProvider.getInstance().getCurrentDeployment().getBullseye());
    }

    public void addTacticalElement(final TacticalElement tacticalElement, final String logPosition) {
        PersistanceProvider.getInstance().getCurrentDeployment().getTacticalSymbolEntries().add(tacticalElement);
        EtbEntry etbEntry = new EtbEntry(Date.from(Instant.now()), ApplicationService.CURRENT_USER, "Tactical Element added: " + logPosition);
        PersistanceProvider.getInstance().getCurrentDeployment().getEtbEntries().add(etbEntry);
        ActionService.notifyActionListeners(new DeploymentUpdatedEvent(this));
        PersistanceProvider.getInstance().saveCurrentDeployment();
    }

    public Collection<TacticalElement> getTacticalElements() {
        return PersistanceProvider.getInstance().getCurrentDeployment().getTacticalSymbolEntries();
    }

}
