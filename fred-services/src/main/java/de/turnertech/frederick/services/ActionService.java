package de.turnertech.frederick.services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class ActionService {
    
    private static final ArrayList<ActionListener> actionListeners = new ArrayList<>();

    private ActionService() {
        // Static central messages
    }

    public static void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public static void notifyActionListeners(final ActionEvent event) {
        SwingUtilities.invokeLater(() -> {
            for(ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(event);
            }
        });
    }

    public static void notifyActionListeners(final Object source, final int event) {
        ActionEvent actionEvent = new ActionEvent(source, event, "");
        notifyActionListeners(actionEvent);
    }

}
