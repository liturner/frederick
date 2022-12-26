package de.turnertech.frederick;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import de.turnertech.frederick.data.Bullseye;
import de.turnertech.frederick.data.EtbEntry;

/**
 * This is the service layer for the application. All actions performed by the GUI
 * should come through here. Consider it the API for the application.
 * 
 * This class should not have any dependency on Geotools, or the GUI in general.
 */
public class Service {
    
    final Database database;

    Service(final Database database) {
        this.database = database;
    }

    /**
     * Sets the current deployments Bullseye position.
     * 
     * @param bullseye Setting to null will clear the bullseye.
     * @param logPosition The bullseye coordinates as they should be entered into the ETB.
     */
    public void setBullseye(final Bullseye bullseye, final String logPosition) {
        database.getCurrentDeployment().setBullseye(bullseye);
        EtbEntry etbEntry = new EtbEntry(Date.from(Instant.now()), Application.CURRENT_USER, "Einsatzort festgelegt als " + logPosition);
        database.getCurrentDeployment().getEtbEntries().add(etbEntry);
        database.notifyActionListeners(Database.DEPLOYMENT_UPDATED_EVENT);
        database.saveCurrentDeployment();
    }

    public Optional<Bullseye> getBullseye() {
        return Optional.ofNullable(database.getCurrentDeployment().getBullseye());
    }

}
