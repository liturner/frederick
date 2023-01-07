package de.turnertech.frederick.persistance.file;

import java.util.TimerTask;

import de.turnertech.frederick.services.PersistanceProvider;

public class SaveTimerTask extends TimerTask {

    private final PersistanceProvider persistanceProvider;

    public SaveTimerTask(final PersistanceProvider persistanceProvider) {
        this.persistanceProvider = persistanceProvider;
    }

    @Override
    public void run() {
        persistanceProvider.saveCurrentDeployment();        
    }
    
}
