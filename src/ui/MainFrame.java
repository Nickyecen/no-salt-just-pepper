package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	final private String WINDOW_TITLE = "No Salt Just Pepper";
	final private int DEFAULT_WINDOW_WIDTH = 800;
	final private int DEFAULT_WINDOW_HEIGHT = 600;
	
	final private String BPM_LABEL = "BPM:";
	final private String INSTRUMENT_LABEL = "Current Instrument:";
	final private String NOTE_LABEL = "Current Note:";
	
	final private String FILE_MENU_LABEL = "File";
	final private String SAVE_MIDI_MENU_ITEM_LABEL = "Save MIDI";
	
	private GridBagLayout layout;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMidiMenuItem;
	
	private TextArea textArea;
	private JButton playButton;
	private JButton stopButton;
	private SpinnerRow bpmRow;
	private TextRow instrumentRow;
	private TextRow noteRow;
	
	private class TextArea extends JScrollPane {
		
		private JTextArea textArea;
		
		private TextArea() {
			this.textArea = new JTextArea();
			this.textArea.setLineWrap(true);
			this.textArea.setWrapStyleWord(true);
			super.setViewportView(this.textArea);
		}
	}
	
	private class SpinnerRow extends JPanel {
		
		private JLabel bpmLabel;
		private JSpinner bpmSpinner;

		private SpinnerRow(String label) {
			GridLayout layout = new GridLayout(1, 2);
			super.setLayout(layout);
			
			this.bpmLabel = new JLabel(label);
			super.add(this.bpmLabel);
			
			this.bpmSpinner = new JSpinner();
			super.add(this.bpmSpinner);
		}
	}
	
	private class TextRow extends JPanel {
		
		private JLabel label;
		private JLabel info;

		private TextRow(String label, String info) {
			GridLayout layout = new GridLayout(1, 2);
			super.setLayout(layout);
			
			this.label = new JLabel(label);
			super.add(this.label);
			
			this.info = new JLabel(info);
			super.add(this.info);
		}
	}
	
	private GridBagConstraints textAreaConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
        c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 4;
    	c.gridheight = 1;
    	c.weighty = 1.0;
    	c.weightx = 1.0;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(10, 10, 10, 10);
    	
    	return c;
	}
	
	private GridBagConstraints playButtonConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
	    c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.weighty = 0.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 10, 10, 10);
		
		return c;
	}
	
	private GridBagConstraints stopButtonConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
	    c.gridy = 1;
    	c.gridwidth = 1;
    	c.gridheight = 3;
    	c.weighty = 0.0;
    	c.weightx = 1.0;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0, 0, 10, 0);
    	
    	return c;
	}
	
	private GridBagConstraints bpmRowConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 2;
	    c.gridy = 1;
    	c.gridwidth = 1;
    	c.gridheight = 1;
    	c.weighty = 0.0;
    	c.weightx = 1.0;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0, 10, 10, 10);
    	
    	return c;
	}

	private GridBagConstraints instrumentRowConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 2;
	    c.gridy = 2;
    	c.gridwidth = 1;
    	c.gridheight = 1;
    	c.weighty = 0.0;
    	c.weightx = 1.0;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0, 10, 10, 10);
    	
    	return c;
	}
	
	private GridBagConstraints noteRowConstraints() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 2;
	    c.gridy = 3;
    	c.gridwidth = 1;
    	c.gridheight = 1;
    	c.weighty = 0.0;
    	c.weightx = 1.0;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets = new Insets(0, 10, 10, 10);
    	
    	return c;
	}
	
    public MainFrame() {
    	
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        
        this.layout = new GridBagLayout();
        setLayout(layout);
        
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);
		this.fileMenu = new JMenu(FILE_MENU_LABEL);
		this.fileMenu.setMnemonic(KeyEvent.VK_F);
		this.menuBar.add(fileMenu);
		this.saveMidiMenuItem = new JMenuItem(SAVE_MIDI_MENU_ITEM_LABEL);
		this.saveMidiMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.fileMenu.add(saveMidiMenuItem);

    	this.textArea = new TextArea();
    	add(this.textArea, textAreaConstraints());
    	
    	ImageIcon icon = new ImageIcon(getClass().getResource("/res/play_icon.png"));
    	this.playButton = new JButton(icon);
	    add(this.playButton, playButtonConstraints());
	    
	    icon = new ImageIcon(getClass().getResource("/res/stop_icon.png"));
	    this.stopButton = new JButton(icon);
	    add(this.stopButton, stopButtonConstraints());
	    
	    this.bpmRow = new SpinnerRow(BPM_LABEL);
	    add(this.bpmRow, bpmRowConstraints());
	    
	    this.instrumentRow = new TextRow(INSTRUMENT_LABEL, "Helicopter (126)");
	    add(this.instrumentRow, instrumentRowConstraints());
	    
	    this.noteRow = new TextRow(NOTE_LABEL, "A4");
	    add(this.noteRow, noteRowConstraints());
    }
}