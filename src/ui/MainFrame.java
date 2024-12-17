package ui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import music.songOrchestrator.Control;
import music.songOrchestrator.Orchestrator;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, ChangeListener, ItemListener {

	final private String WINDOW_TITLE = "No Salt Just Pepper";
	final private int DEFAULT_WINDOW_WIDTH = 800;
	final private int DEFAULT_WINDOW_HEIGHT = 600;

	final private String PLAY_ICON_PATH = "/res/play_icon.png";
	final private String PAUSE_ICON_PATH = "/res/pause_icon.png";
	final private String STOP_ICON_PATH = "/res/stop_icon.png";
	final private String LOGO_ICON_PATH = "/res/logo_icon.png";

	final private int BPM_MIN = 1;
	final private int BPM_MAX = 999;
	final private int BPM_STEP = 10;

	final private String PLAY_ACTION = "play";
	final private String PAUSE_ACTION = "pause";
	final private String STOP_ACTION = "stop";
	final private String OPEN_TEXT_FILE_ACTION = "openTextFile";
	final private String SAVE_MIDI_ACTION = "saveMidi";
	final private String UNDO_ACTION = "undo";
	final private String REDO_ACTION = "redo";

	final private String BPM_LABEL = "BPM:";
	final private String INSTRUMENT_LABEL = "Current Instrument:";
	final private String NOTE_LABEL = "Current Note:";
	final private String NO_INFO = " - ";

	final private String FILE_MENU_LABEL = "File";
	final private String OPEN_TEXT_FILE_MENU_ITEM_LABEL = "Open Text File";
	final private String SAVE_MIDI_MENU_ITEM_LABEL = "Save MIDI";
	final private String EDIT_MENU_LABEL = "Edit";
	final private String UNDO_MENU_ITEM_LABEL = "Undo";
	final private String REDO_MENU_ITEM_LABEL = "Redo";
	final private String WINDOW_MENU_LABEL = "Window";
	final private String DARK_THEME_MENU_ITEM_LABEL = "Toggle Dark Theme";

	private Orchestrator orchestrator;

	private GridBagLayout layout;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openTextFileMenuItem;
	private JMenuItem saveMidiMenuItem;
	private JMenu editMenu;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenu windowMenu;
	private JMenuItem darkThemeMenuItem;

	private TextArea textArea;
	private JButton playButton;
	private JButton stopButton;
	private SpinnerRow bpmRow;
	private TextRow instrumentRow;
	private TextRow noteRow;

	private ImageIcon logoIcon;
	private ImageIcon playIcon;
	private ImageIcon pauseIcon;
	private ImageIcon stopIcon;

	private JFileChooser fileChooser;
	private TextFilter textFilter;
	private MidiFilter midiFilter;			

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

	@Override
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
		else if (UNDO_ACTION.equals(e.getActionCommand())) {
			this.textArea.undo();
		}
		else if (REDO_ACTION.equals(e.getActionCommand())) {
			this.textArea.redo();
		}
		else if (OPEN_TEXT_FILE_ACTION.equals(e.getActionCommand())) {

			this.fileChooser.setFileFilter(this.textFilter);
			this.fileChooser.removeChoosableFileFilter(this.midiFilter);

			int returnVal = this.fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = this.fileChooser.getSelectedFile();

                try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                	this.textArea.clear();
                	String line = null;
            	    while ((line = reader.readLine()) != null) {
            	    	this.textArea.append(line + "\n");
            	    }
				} catch (IOException e1) {
					e1.printStackTrace();
				}

            }
		}
		else if (SAVE_MIDI_ACTION.equals(e.getActionCommand())) {

			this.fileChooser.setFileFilter(this.midiFilter);
			this.fileChooser.removeChoosableFileFilter(this.textFilter);

			int returnVal = this.fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = this.fileChooser.getSelectedFile();

                // TODO: Implementar salvamento de MIDi
            }
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.orchestrator.setBpm(this.bpmRow.getNumber());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getItemSelectable() == this.darkThemeMenuItem) {

			if (e.getStateChange() == ItemEvent.DESELECTED) {
				FlatMacLightLaf.setup();
			}
			else {
				FlatMacDarkLaf.setup();
			}

			FlatLaf.updateUI();
			SwingUtilities.updateComponentTreeUI(this.fileChooser);
		}
	}

	private void setPlayState() {
		this.playButton.setIcon(pauseIcon);
		this.playButton.setActionCommand(PAUSE_ACTION);
		this.stopButton.setEnabled(true);
		
		this.orchestrator.setSongRequest(textArea.getText());
		this.orchestrator.play();
	}

	private void setPauseState() {

		this.playButton.setIcon(playIcon);
		this.playButton.setActionCommand(PLAY_ACTION);
		this.orchestrator.pause();
	}

	public void setStopState() {

		setPauseState();
		this.stopButton.setEnabled(false);
		this.orchestrator.stop();
	}

	public void setInstrument(String instrument) {
		this.instrumentRow.setInfo(instrument);
	}

	public void setNote(String note) {
		this.noteRow.setInfo(note);
	}

	public MainFrame(Orchestrator orchestrator) {

		this.orchestrator = orchestrator;

		this.logoIcon = new ImageIcon(getClass().getResource(LOGO_ICON_PATH));
		this.playIcon = new ImageIcon(getClass().getResource(PLAY_ICON_PATH));
		this.pauseIcon = new ImageIcon(getClass().getResource(PAUSE_ICON_PATH));
		this.stopIcon = new ImageIcon(getClass().getResource(STOP_ICON_PATH));
		
		setIconImage(this.logoIcon.getImage());

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
		this.openTextFileMenuItem = new JMenuItem(OPEN_TEXT_FILE_MENU_ITEM_LABEL);
		this.openTextFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		this.openTextFileMenuItem.setActionCommand(OPEN_TEXT_FILE_ACTION);
		this.openTextFileMenuItem.addActionListener(this);
		this.fileMenu.add(openTextFileMenuItem);
		this.saveMidiMenuItem = new JMenuItem(SAVE_MIDI_MENU_ITEM_LABEL);
		this.saveMidiMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.saveMidiMenuItem.setActionCommand(SAVE_MIDI_ACTION);
		this.saveMidiMenuItem.addActionListener(this);
		this.fileMenu.add(saveMidiMenuItem);

		this.fileChooser = new JFileChooser();
		this.textFilter = new TextFilter();
		this.midiFilter = new MidiFilter();
		this.fileChooser.setAcceptAllFileFilterUsed(false);

		this.editMenu = new JMenu(EDIT_MENU_LABEL);
		this.editMenu.setMnemonic(KeyEvent.VK_E);
		this.menuBar.add(editMenu);
		this.undoMenuItem = new JMenuItem(UNDO_MENU_ITEM_LABEL);
		this.undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		this.undoMenuItem.setActionCommand(UNDO_ACTION);
		this.undoMenuItem.addActionListener(this);
		this.editMenu.add(undoMenuItem);
		this.redoMenuItem = new JMenuItem(REDO_MENU_ITEM_LABEL);
		this.redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		this.redoMenuItem.setActionCommand(REDO_ACTION);
		this.redoMenuItem.addActionListener(this);
		this.editMenu.add(redoMenuItem);

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

<<<<<<< HEAD
		this.bpmRow = new SpinnerRow(BPM_LABEL, BPM_MIN, BPM_MAX, BPM_STEP, Control.getDefaultBPM());
		this.bpmRow.getSpinner().addChangeListener(this);
=======
		orchestrator.setBpm(DEFAULT_BPM);
		this.bpmRow = new SpinnerRow(BPM_LABEL, BPM_MIN, BPM_MAX, BPM_STEP, DEFAULT_BPM);
		this.bpmRow.addChangeListener(this);
>>>>>>> 9b48912fad1009c08466766e99fb4bc221ad244a
		add(this.bpmRow, bpmRowConstraints());

		this.instrumentRow = new TextRow(INSTRUMENT_LABEL, NO_INFO);
		add(this.instrumentRow, instrumentRowConstraints());

		this.noteRow = new TextRow(NOTE_LABEL, NO_INFO);
		add(this.noteRow, noteRowConstraints());
	}
}