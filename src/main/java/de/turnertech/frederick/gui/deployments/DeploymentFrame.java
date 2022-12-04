package de.turnertech.frederick.gui.deployments;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.turnertech.frederick.Resources;

public class DeploymentFrame extends JFrame  {
    
    public DeploymentFrame() {
        this.setTitle("Frederick - Einsatz Manager");
        this.setIconImage(Resources.getdeployment24pxIcon().getImage());
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(400, 400);        
    }

}
