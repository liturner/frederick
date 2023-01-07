package de.turnertech.frederick.services;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import de.turnertech.frederick.data.Deployment;

public abstract class PersistanceProvider {

    public static final String CURRENT_DEPLOYMENT_FILE_NAME = "Current";
    
    private static PersistanceProvider instance;

    // Singleton Service, error on multiple instances
    public static PersistanceProvider getInstance() {
        if(instance != null) {
            return instance;
        }

        ServiceLoader<PersistanceProvider> services = ServiceLoader.load(PersistanceProvider.class);
        instance = services.findFirst().orElse(null);

        if(instance == null) {
            Logging.LOGGER.severe("No Persistance Service found!");
            System.exit(-1);
        }

        return instance;
    }

    public abstract void closeDeployment(final String name);

    public abstract Optional<Path> getPathToDeployment(final String name);

    public abstract boolean isDeploymentExists(final String name);

    public abstract void deleteDeployment(final String name);

    public abstract Deployment getCurrentDeployment();

    public abstract List<File> getDeploymentFiles();

    public abstract void saveCurrentDeployment();

    public abstract void saveCurrentDeploymentInFiveSeconds();

}
