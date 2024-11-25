package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import main.Main;
import music.songOrchestrator.Orchestrator;
import music.songOrchestrator.Status;
import utilitaries.MealyMachine;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, ChangeListener, ItemListener {

	final private String WINDOW_TITLE = "No Salt Just Pepper";
	final private int DEFAULT_WINDOW_WIDTH = 800;
	final private int DEFAULT_WINDOW_HEIGHT = 600;
	
	final private String PLAY_ICON_PATH = "/res/play_icon.png";
	final private String PAUSE_ICON_PATH = "/res/pause_icon.png";
	final private String STOP_ICON_PATH = "/res/stop_icon.png";
	
	final private int DEFAULT_BPM = 120;
	final private int BPM_MIN = 1;
	final private int BPM_MAX = 999;
	final private int BPM_STEP = 10;
	
	final private String PLAY_ACTION = "play";
	final private String PAUSE_ACTION = "pause";
	final private String STOP_ACTION = "stop";
	final private String SAVE_MIDI_ACTION = "saveMidi";

	final private String BPM_LABEL = "BPM:";
	final private String INSTRUMENT_LABEL = "Current Instrument:";
	final private String NOTE_LABEL = "Current Note:";
	final private String NO_INFO = " - ";

	final private String FILE_MENU_LABEL = "File";
	final private String SAVE_MIDI_MENU_ITEM_LABEL = "Save MIDI";
	final private String WINDOW_MENU_LABEL = "Window";
	final private String DARK_THEME_MENU_ITEM_LABEL = "Toggle Dark Theme";

	private Main mainInstance;

	private GridBagLayout layout;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMidiMenuItem;
	private JMenu windowMenu;
	private JMenuItem darkThemeMenuItem;

	private TextArea textArea;
	private JButton playButton;
	private JButton stopButton;
	private SpinnerRow bpmRow;
	private TextRow instrumentRow;
	private TextRow noteRow;

	private ImageIcon playIcon;
	private ImageIcon pauseIcon;
	private ImageIcon stopIcon;
	
	private class TextArea extends JScrollPane {

		private JTextArea textArea;

		private TextArea() {
			this.textArea = new JTextArea();
			this.textArea.setLineWrap(true);
			this.textArea.setWrapStyleWord(true);
			this.setViewportView(this.textArea);
		}
	}

	private class SpinnerRow extends JPanel {

		private JLabel label;
		private JSpinner spinner;
		private SpinnerNumberModel model;

		private SpinnerRow(String label, int minimum, int maximum, int stepSize, int defaultValue) {
			GridLayout layout = new GridLayout(1, 2);
			this.setLayout(layout);

			this.label = new JLabel(label);
			this.add(this.label);
			
			this.model = new SpinnerNumberModel();
			this.model.setMinimum(minimum);
			this.model.setMaximum(maximum);
			this.model.setStepSize(stepSize);
			this.model.setValue(defaultValue);
			
			this.spinner = new JSpinner(this.model);
			this.add(this.spinner);
		}
	}

	private class TextRow extends JPanel {

		private JLabel label;
		private JLabel info;

		private TextRow(String label, String info) {
			GridLayout layout = new GridLayout(1, 2);
			this.setLayout(layout);

			this.label = new JLabel(label);
			this.add(this.label);

			this.info = new JLabel(info);
			this.add(this.info);
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

	
	public void actionPerformed(ActionEvent e) {

		if (PLAY_ACTION.equals(e.getActionCommand())) {
			setPlayState();
		}
		else if (PAUSE_ACTION.equals(e.getActionCommand())) {
			setPauseState();
		}
		else if (STOP_ACTION.equals(e.getActionCommand())) {
			setStopState();
		}
		else if (SAVE_MIDI_ACTION.equals(e.getActionCommand())) {

		}
	}

	
	public void stateChanged(ChangeEvent e) {
		this.mainInstance.setBpm((int) this.bpmRow.model.getNumber());
	}
	
	
	public void itemStateChanged(ItemEvent e) {
		if (e.getItemSelectable() == this.darkThemeMenuItem) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				FlatMacLightLaf.setup();
				FlatMacLightLaf.updateUI();
			}
			else {
				FlatMacDarkLaf.setup();
				FlatMacDarkLaf.updateUI();
			}
		}
	}
	
	private void setPlayState() {

		this.playButton.setIcon(pauseIcon);
		this.playButton.setActionCommand(PAUSE_ACTION);
		this.stopButton.setEnabled(true);
		this.mainInstance.play();
	}

	
	private void setPauseState() {

		this.playButton.setIcon(playIcon);
		this.playButton.setActionCommand(PLAY_ACTION);
		this.mainInstance.pause();
	}

	
	public void setStopState() {

		setPauseState();
		this.stopButton.setEnabled(false);
		this.mainInstance.stop();
	}

	
	public void setInstrument(String instrument) {
		this.instrumentRow.info.setText(instrument);
	}
	
	public void setNote(String note) {
		this.noteRow.info.setText(note);
	}
	
	public MainFrame(Main mainInstance) {
		
		this.mainInstance = mainInstance;
		
		this.playIcon = new ImageIcon(getClass().getResource(PLAY_ICON_PATH));
		this.pauseIcon = new ImageIcon(getClass().getResource(PAUSE_ICON_PATH));
		this.stopIcon = new ImageIcon(getClass().getResource(STOP_ICON_PATH));

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
		this.saveMidiMenuItem.setActionCommand(SAVE_MIDI_ACTION);
		this.saveMidiMenuItem.addActionListener(this);
		this.fileMenu.add(saveMidiMenuItem);
		
		this.windowMenu = new JMenu(WINDOW_MENU_LABEL);
		this.windowMenu.setMnemonic(KeyEvent.VK_W);
		this.menuBar.add(windowMenu);
		this.darkThemeMenuItem = new JCheckBoxMenuItem(DARK_THEME_MENU_ITEM_LABEL);
		this.darkThemeMenuItem.addItemListener(this);
		this.windowMenu.add(darkThemeMenuItem);

		this.textArea = new TextArea();
		add(this.textArea, textAreaConstraints());

		this.playButton = new JButton(this.playIcon);
		this.playButton.setActionCommand(PLAY_ACTION);
		this.playButton.addActionListener(this);
		add(this.playButton, playButtonConstraints());

		this.stopButton = new JButton(this.stopIcon);
		this.stopButton.setActionCommand(STOP_ACTION);
		this.stopButton.setEnabled(false);
		this.stopButton.addActionListener(this);
		add(this.stopButton, stopButtonConstraints());

		mainInstance.setBpm(DEFAULT_BPM);
		this.bpmRow = new SpinnerRow(BPM_LABEL, BPM_MIN, BPM_MAX, BPM_STEP, DEFAULT_BPM);
		this.bpmRow.spinner.addChangeListener(this);
		add(this.bpmRow, bpmRowConstraints());

		this.instrumentRow = new TextRow(INSTRUMENT_LABEL, NO_INFO);
		add(this.instrumentRow, instrumentRowConstraints());

		this.noteRow = new TextRow(NOTE_LABEL, NO_INFO);
		add(this.noteRow, noteRowConstraints());
	}
}