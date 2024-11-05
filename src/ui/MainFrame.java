package ui;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class MainFrame extends JFrame {
    public MainFrame() {
    	FlatMacLightLaf.setup();
    	
        setTitle("Gerador de Música por Texto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Text Area
        c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 4;
    	c.gridheight = 1;
    	c.weighty = 1.0;
    	c.weightx = 1;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(10, 10, 10, 10);
    	JTextArea textArea = new JTextArea();
    	textArea.setLineWrap(true);
    	textArea.setWrapStyleWord(true);
    	JScrollPane scrollPane = new JScrollPane(textArea);
    	add(scrollPane, c);
    	
    	// Play Button
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/play_icon.png"));
        c.gridx = 0;
    	c.gridy = 1;
    	c.gridwidth = 1;
    	c.gridheight = 3;
    	c.weighty = 0.0;
    	c.weightx = 1.0;
    	c.insets = new Insets(0, 10, 10, 10);
	    add(new JButton(icon), c);
	    
	    // Stop Button
	    icon = new ImageIcon(getClass().getResource("/res/stop_icon.png"));
	    c.gridx = 1;
	    c.gridy = 1;
	    c.insets = new Insets(0, 0, 10, 0);
	    add(new JButton(icon), c);
	    
	    // BPM Spinner
	    c.gridx = 2;
	    c.gridy = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.LINE_END;
	    c.insets = new Insets(0, 10, 10, 5);
	    add(new JLabel("BPM:"), c);
	    c.gridx = 3;
	    c.gridy = 1;
	    c.fill = GridBagConstraints.BOTH;
	    c.anchor = GridBagConstraints.LINE_START;
	    c.insets = new Insets(0, 0, 10, 10);
	    add(new JSpinner(), c);
	    
	    // Instrument information
	    c.gridx = 2;
	    c.gridy = 2;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.LINE_END;
	    c.insets = new Insets(0, 10, 10, 5);
	    add(new JLabel("Current Instrument:"), c);
	    c.gridx = 3;
	    c.gridy = 2;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.LINE_START;
	    c.insets = new Insets(0, 0, 10, 10);
	    add(new JLabel("Helicopter (126)"), c);
	    
	    // Note information
	    c.gridx = 2;
	    c.gridy = 3;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.LINE_END;
	    c.insets = new Insets(0, 10, 10, 5);
	    add(new JLabel("Current Note:"), c);
	    c.gridx = 3;
	    c.gridy = 3;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.LINE_START;
	    c.insets = new Insets(0, 0, 10, 10);
	    add(new JLabel("A4"), c);
    }
}