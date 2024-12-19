package main;

import javax.swing.SwingUtilities;

import org.jfugue.pattern.Pattern;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import music.songOrchestrator.Orchestrator;
import stateMachine.StateMachine;
import ui.MainFrame;

import utilitaries.InputAdapter;

public class Main {	

    public static void main(String[] args) {
    
    	final Orchestrator orchestrator = new Orchestrator();
    	
    	// Apply modern theme
    	FlatMacLightLaf.setup();

        // Use SwingUtilities to ensure that the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(orchestrator);
            mainFrame.setVisible(true);
        });
    
    }
    
}