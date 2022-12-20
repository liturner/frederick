package de.turnertech.frederick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.turnertech.frederick.data.Deployment;
import de.turnertech.frederick.data.SaveTimerTask;

public class Database {
    
    private final Path root;

    public static final String CURRENT_DEPLOYMENT_FILE_NAME = "Current";

    public static final Integer DEPLOYMENT_CLOSED_EVENT = "DEPLOYMENT_CLOSED_EVENT".hashCode();

    public static final Integer DEPLOYMENT_SAVED_EVENT = "DEPLOYMENT_SAVED_EVENT".hashCode();

    public static final Integer DEPLOYMENT_DELETED_EVENT = "DEPLOYMENT_DELETED_EVENT".hashCode();

    private final ArrayList<ActionListener> actionListeners = new ArrayList<>();

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

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public void notifyActionListeners(int event) {
        ActionEvent actionEvent = new ActionEvent(this, event, "");
        for(ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(actionEvent);
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
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .sorted(Comparator.comparingLong(File::lastModified).reversed())
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
            Path pathToDeployment = getPathToDeployment(CURRENT_DEPLOYMENT_FILE_NAME).orElseThrow();
            Serialization.serialize(currentDeployment, pathToDeployment.toString());
            Logging.LOGGER.info("Saved current deployment");
            notifyActionListeners(DEPLOYMENT_SAVED_EVENT);
        } catch (NoSuchElementException e) {
            Logging.LOGGER.severe("Unable to get path to current deployment! Cannot save current deployment!");
        } catch (Exception e) {
            Logging.LOGGER.severe("Unable to save the deployment!");
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
     * Returns the path to a deployment with the specified name. Note, that
     * the deployment does not need to exist! This function is rather a 
     * helper function for concatenating the desired deployment name to the
     * store root folder path.
     * 
     * @param name The name of the deployment to get a path for.
     * @return {@link Optional#empty()} on failure, or a valid theoretical {@link Path} .
     */
    public Optional<Path> getPathToDeployment(final String name) {
        Path returnPath = null;
        if(name == null || "".equals(name)) {
            return Optional.empty();
        }        
        try {
            returnPath = Path.of(root.toString(), name);
        } 
        catch (Exception e) {
            Logging.LOGGER.severe(() -> "Could not get path to deployment. Is it a valid name?: " + name);
        }
        return Optional.ofNullable(returnPath);
    }

    public Optional<Deployment> getDeployment(final String name) {
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

    /**
     * Simple check for if a deployment with the given name exists on disk.
     * 
     * @param name The name of a deployment to check for.
     * @return Whether the deployment exists or not.
     */
    public boolean isDeploymentExists(final String name) {
        if(name == null || "".equals(name)) {
            return false;
        }
        Path pathToDeployment = getPathToDeployment(name).orElse(null);
        return Files.exists(pathToDeployment);
    }

    public void closeDeployment(final String name) {
        try {
            Path pathToDeployment = getPathToDeployment(name).orElse(null);
            Serialization.serialize(currentDeployment, pathToDeployment.toString());
            Logging.LOGGER.log(Level.INFO, "Wrote current deployment to new file");

            pathToDeployment = getPathToDeployment(CURRENT_DEPLOYMENT_FILE_NAME).orElse(null);
            Files.delete(pathToDeployment);
            currentDeployment = null;
            Logging.LOGGER.info("Deleted old current");
            // This triggers creation of the new empty deployment
            getCurrentDeployment();
            saveCurrentDeployment();
            notifyActionListeners(DEPLOYMENT_CLOSED_EVENT);
        } catch (IOException e) {
            Logging.LOGGER.severe("Unable to close the deployment!");
        }
    }

    public void deleteDeployment(final String name) {
        try {
            Path pathToDeployment = getPathToDeployment(name).orElse(null);
            Files.delete(pathToDeployment);
            Logging.LOGGER.info(() -> "\"" + Application.CURRENT_USER + "\" hat den Einsatz \"" + name + "\" gelöscht");
            notifyActionListeners(DEPLOYMENT_DELETED_EVENT);
        } catch (IOException e) {
            Logging.LOGGER.severe("Unable to delete the deployment!");
        }
    }

}
