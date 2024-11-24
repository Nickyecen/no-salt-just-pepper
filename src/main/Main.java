package main;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import music.songOrchestrator.Orchestrator;
import ui.MainFrame;
import utilitaries.MealyMachine;

public class Main {
    public static void main(String[] args) {

    	// Apply modern theme
    	FlatMacLightLaf.setup();

        // Use SwingUtilities to ensure that the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    	
        // Orchestrator test
    	Scanner s = new Scanner(System.in);
    	Orchestrator o = new Orchestrator(new MealyMachine(null, null));
    	o.getControl().setBpm(240);
    	
    	while(true) {
    		String action = s.next();
    		System.out.println(action);
    		if(action.equals("stop")) {
    			o.stop();
    			System.out.println("Stopped!");
    		}
    		if(action.equals("pause")) {
    			o.pause();
    			System.out.println("Paused!");
    		}
    		if(action.equals("play")) {
    			o.play();
    			System.out.println("Playing!");
    		}
    	}
    }
}