package main;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import music.songOrchestrator.Orchestrator;
import music.songOrchestrator.Status;
import ui.MainFrame;
import utilitaries.MealyMachine;

public class Main {
	
	private Orchestrator orchestrator;

    public static void main(String[] args) {
    	
    	Main mainInstance = new Main();
    	
    	// Apply modern theme
    	FlatMacLightLaf.setup();

        // Use SwingUtilities to ensure that the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(mainInstance);
            mainFrame.setVisible(true);
        });
        
        // Orchestrator test
    	mainInstance.orchestrator = new Orchestrator(new MealyMachine(null, null));
    }
    
    public void setBpm(int bpm) {
    	this.orchestrator.getControl().setBpm(bpm);
    }
    
    public void play() {
    	this.orchestrator.play();
    }
    
    public void pause() {
    	this.orchestrator.pause();
    }
    
    public void stop() {
    	this.orchestrator.stop();
    }
}