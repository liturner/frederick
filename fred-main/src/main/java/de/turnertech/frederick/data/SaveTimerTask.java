package de.turnertech.frederick.data;

import java.util.TimerTask;

import de.turnertech.frederick.main.Application;

public class SaveTimerTask extends TimerTask {

    @Override
    public void run() {
        Application.getDatabase().saveCurrentDeployment();        
    }
    
}
