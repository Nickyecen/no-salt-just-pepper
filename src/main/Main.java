package main;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import ui.MainFrame;

public class Main {
    public static void main(String[] args) {

    	// Apply modern theme
    	FlatMacLightLaf.setup();

        // Use SwingUtilities to ensure that the UI updates happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    	
    }
}