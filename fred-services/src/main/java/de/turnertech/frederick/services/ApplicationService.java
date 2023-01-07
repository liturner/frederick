package de.turnertech.frederick.services;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;

import de.turnertech.frederick.data.Bullseye;
import de.turnertech.frederick.data.TacticalElement;

public abstract class ApplicationService {

    public static final String CURRENT_USER = System.getProperty("user.name");

    // Singleton Service, error on multiple instances
    public static ApplicationService getInstance() {
        ServiceLoader<ApplicationService> services = ServiceLoader.load(ApplicationService.class);
        ApplicationService applicationService = services.findFirst().orElse(null);

        if(applicationService == null) {
            Logging.LOGGER.severe("No Application Service found!");
            System.exit(-1);
        }

        return applicationService;
    }

    public abstract void setBullseye(final Bullseye bullseye, final String logPosition);

    public abstract Optional<Bullseye> getBullseye();

    public abstract void addTacticalElement(final TacticalElement tacticalElement, final String logPosition);

    public abstract Collection<TacticalElement> getTacticalElements();
    
}
