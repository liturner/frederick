package de.turnertech.frederick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.turnertech.frederick.data.Deployment;
import de.turnertech.frederick.data.SaveTimerTask;

public class Database {
    
    private final Path root;

    public static final String CURRENT_DEPLOYMENT_FILE_NAME = "Current.dep";

    private final Timer saveTimer = new Timer("Save Timer");

    private Optional<SaveTimerTask> saveTimerTask = Optional.empty();

    private Deployment currentDeployment;

    /**
     * The constructor initialises the data storage folder.
     */
    public Database() {
        // Handle cross platform by changing this property during the build pipeline!
        String rootFolder = System.getProperty("de.turnertech.frederick.store.location");
        String subFolder = System.getProperty("de.turnertech.frederick.store.deployment.folder", "Deployments");

        root = Path.of(rootFolder, subFolder);

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            Logging.LOGGER.log(Level.SEVERE, "Could not create database. Exiting!", e);
            Application.exit();
        }

    }

    /**
     * Return the sorted set of files stored in the deployments folder
     * of the data store. This will not cause the files to be loaded into memory.
     * 
     * @return Never null, empty list on exception.
     */
    public List<File> getDeploymentFiles() {
        try (Stream<Path> stream = Files.list(root)) {
            return stream
                .filter(file -> Files.isRegularFile(file))
                .map(Path::toFile)
                .sorted()
                .collect(Collectors.toList());
        } catch (Exception e) {
            Logging.LOGGER.severe("Could not list deployment files!");
        }

        return List.of();
    }

    public List<Deployment> getDeployments() {
        return null;
    }

    /**
     * Always returns a deployment! If no deployment is loaded, then it will load
     * or create one.
     * 
     * @return The active deployment.
     */
    public Deployment getCurrentDeployment() {
        if(currentDeployment == null) {
            Optional<Deployment> optionalDeployment = getDeployment(CURRENT_DEPLOYMENT_FILE_NAME);
            if(optionalDeployment.isPresent()) {
                currentDeployment = optionalDeployment.get();
                Logging.LOGGER.log(Level.INFO, "Loaded existing current deployment");
            } else {
                currentDeployment = new Deployment();
                Logging.LOGGER.log(Level.INFO, "Created new current deployment");
            }
        }
        return currentDeployment;
    }

    public void saveCurrentDeployment() {
        try {
            Path pathToDeployment = getPathToDeployment(CURRENT_DEPLOYMENT_FILE_NAME);
            Serialization.serialize(currentDeployment, pathToDeployment.toString());
            Logging.LOGGER.log(Level.INFO, "Saved current deployment");
        } catch (IOException e) {
            Logging.LOGGER.log(Level.SEVERE, "Unable to save the deployment!");
        }
    }

    /**
     * Manages a save timer which triggers in 5 seconds. Every call to this function
     * resets the timer. This is usefull for enabling autosave functionality for
     * example when saving after text bodies are entered.
     */
    public void saveCurrentDeploymentInFiveSeconds() {
        if(saveTimerTask.isPresent()) {
            saveTimerTask.get().cancel();
        }
        saveTimerTask = Optional.of(new SaveTimerTask());
        saveTimer.schedule(saveTimerTask.get(), 5000);
    }

    /**
     * Always returns a File, although the file it refers to may not exist!
     * 
     * @param name
     * @return
     */
    private Path getPathToDeployment(String name) {
        return Path.of(root.toString(), name);
    }

    public Optional<Deployment> getDeployment(String name) {
        try {
            Path pathToFile = Path.of(root.toString(), name);
            if(!pathToFile.toFile().exists()) {
                return Optional.empty();
            }
            Object deployment = Serialization.deserialize(pathToFile.toString());
            if(deployment instanceof Deployment) {
                return Optional.of((Deployment)deployment);
            }
        } catch (ClassNotFoundException | IOException e) {
            Logging.LOGGER.log(Level.WARNING, "Could not load deployment %s", name);
            return Optional.empty();
        }
        return Optional.empty();
    }

}
