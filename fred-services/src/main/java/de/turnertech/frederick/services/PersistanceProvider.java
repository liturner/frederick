package de.turnertech.frederick.services;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import de.turnertech.frederick.data.Deployment;

public interface PersistanceProvider {

    public static final String CURRENT_DEPLOYMENT_FILE_NAME = "Current";

    public static final int DEPLOYMENT_OPENED_EVENT_ID = "DEPLOYMENT_OPENED_EVENT".hashCode();

    public static final int DEPLOYMENT_CLOSED_EVENT_ID = "DEPLOYMENT_CLOSED_EVENT".hashCode();

    public static final int DEPLOYMENT_SAVED_EVENT_ID = "DEPLOYMENT_SAVED_EVENT".hashCode();

    public static final int DEPLOYMENT_UPDATED_EVENT_ID = "DEPLOYMENT_UPDATED_EVENT".hashCode();

    public static final int DEPLOYMENT_DELETED_EVENT_ID = "DEPLOYMENT_DELETED_EVENT".hashCode();
    
    static List<PersistanceProvider> getInstances() {
        ServiceLoader<PersistanceProvider> services = ServiceLoader.load(PersistanceProvider.class);
        List<PersistanceProvider> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    public void closeDeployment(final String name);

    public Optional<Path> getPathToDeployment(final String name);

    public boolean isDeploymentExists(final String name);

    public void deleteDeployment(final String name);

    public Deployment getCurrentDeployment();

    public List<File> getDeploymentFiles();

    public void saveCurrentDeployment();

    public void saveCurrentDeploymentInFiveSeconds();

}
