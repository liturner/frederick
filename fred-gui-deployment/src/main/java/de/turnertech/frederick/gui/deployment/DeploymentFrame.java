package de.turnertech.frederick.gui.deployment;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.turnertech.frederick.gui.common.StatusBar;
import de.turnertech.frederick.services.Resources;

public class DeploymentFrame extends JFrame  {
    
    public DeploymentFrame() {
        this.setTitle("Frederick - Einsatz Manager");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(400, 400);

        DeploymentTablePane tablePane = new DeploymentTablePane();
        DeploymentToolBar toolbar = new DeploymentToolBar(tablePane.getTable());

        this.add(tablePane, BorderLayout.CENTER);
        this.add(toolbar, BorderLayout.PAGE_START);
        this.add(new StatusBar(), BorderLayout.PAGE_END);
    }

}
