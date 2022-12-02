package de.turnertech.frederick;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;

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

        if(System.getProperty("os.name").contains("indow")) {
            root = Path.of("C:\\ProgramData\\Frederick");
        } else {
            root = Path.of(System.getProperty("user.home"), ".frederick");
        }

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            Logging.LOGGER.log(Level.SEVERE, "Could not create database. Exiting!", e);
            Application.exit();
        }

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
