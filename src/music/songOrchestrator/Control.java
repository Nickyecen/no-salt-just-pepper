package music.songOrchestrator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlCommands.CommandChangeInstrument;
import controlCommands.CommandMakeControlDo;
import controlCommands.CommandPlayNote;
import controlCommands.CommandRaiseBPM;
import music.CommandControl;
import music.Instrument;
import music.MusicPlayer;
import music.Note;
import stateMachine.MealyTransition;
import stateMachine.State;
import stateMachine.StateMachine;

/**
 * Class that will directly control the {@link stateMachine.StateMachine} responsible for reading the song and passing the instructions to the {@link music.MusicPlayer}
 * 
 * @author nickyecen
 */
public class Control implements Runnable {
	
	final private static int MILISECS_IN_MINUTE = 60000;
	final private static int DEFAULT_BPM = 120;
	final private static int RANDOM_BPM_MIN = 60;
	final private static int RANDOM_BPM_MAX = 120;
	final private Random RANDOM;
	final private static String FILE_PATH = "src/res/orchestraMachine.json";
	
	private StateMachine<ArrayList<String>, String> composition;
	private int bpm = DEFAULT_BPM;

	private int milisecsPerBeat = MILISECS_IN_MINUTE / bpm;
	private Status status = Status.STOPPED;
	private MusicPlayer player;

	private boolean shouldWait = true;
	private Thread thread;
	
	/**
	 * Constructs the {@link music.songOrchestrator.Control} 
	 */
	public Control() {
		MusicPlayer.Builder builder = new MusicPlayer.Builder();
		this.player = builder.build();
		this.RANDOM = new Random();
		this.composition = loadStateMachine(FILE_PATH);
	}

	/**
	 * Loads the orchestra {@link stateMachine.StateMachine} with {@link stateMachine.MealyTransition} from a .json file with the format
	 * { "[stateName]": {"[Symbol]": {"nextState": "[stateName]", "command": "[commandName]"}, ...}, ...}
	 * 
	 * @param filePath the path to the .json file
	 * 
	 * @return the loaded orchestra {@link stateMachine.StateMachine}
	 */
	private StateMachine<ArrayList<String>, String> loadStateMachine(String filePath) {
		
		ObjectMapper mapper = new ObjectMapper();
		File fileObj = new File(filePath);
		HashMap<String, HashMap<String, HashMap<String, String>>> data = null;
		try {
			
			data = mapper.readValue(fileObj, new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		HashMap<String, State<String>> stateMap = new HashMap<String, State<String>>();
		
		for(Map.Entry<String, HashMap<String, HashMap<String, String>>> entry : data.entrySet()) {
			stateMap.put(entry.getKey(), new State<String>(true));
		}
		
		StateMachine<ArrayList<String>, String> orchestra = new StateMachine<ArrayList<String>, String>(stateMap.get("q0"));
				
		for(Map.Entry<String, HashMap<String, HashMap<String, String>>> stateReference : data.entrySet()) {
			State<String> state = stateMap.get(stateReference.getKey());
			for(Map.Entry<String, HashMap<String, String>> symbolReference : stateReference.getValue().entrySet()) {
				String symbol = symbolReference.getKey();
				String nextStateName = symbolReference.getValue().get("nextState");
				String commandName = symbolReference.getValue().get("command");		
				State<String> nextState = stateMap.get(nextStateName);
		
				CommandControl command;
				
				if(commandName.contains("playNote")) {
					Note note = Note.valueOf(commandName.subSequence(8, commandName.length()).toString());
					command = new CommandPlayNote(this, note);
					
				} else if(commandName.contains("changeInstrumentTo")) {
					
					int instrumentNumber = Integer.valueOf(commandName.subSequence(18, commandName.length()).toString());
					Instrument instrument = Instrument.fromMidiCode(instrumentNumber);
					command = new CommandChangeInstrument(this, instrument);
					
				} else if(commandName.contains("raiseBPMBy")) {
					
					int amount = Integer.valueOf(commandName.subSequence(10, commandName.length()).toString());
					command = new CommandRaiseBPM(this, amount);
					
				} else {	
					command = new CommandMakeControlDo(this, commandName);					
				}
				
				
				state.addTransition(symbol, MealyTransition.to(nextState).doing(command));
			}
		}
		
		return orchestra;
	}

	/**
	 * Runs the {@link stateMachine.StateMachine} to play the song
	 */
	public void run() {
		setStatus(Status.PLAYING);

		boolean shouldContinue = composition.nextState() && getStatus() != Status.STOPPED;
		while(shouldContinue) {
	
			if(shouldWait) {	
				try {
					Thread.sleep(milisecsPerBeat);
				} catch (InterruptedException e) {
					break;
				}
			}
			
			if(getStatus() == Status.PAUSED) {
				
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						break;
					}
				}
				
			} else if(getStatus() == Status.STOPPED) {				
				break;				
			} else if(getStatus() != Status.PLAYING) {

				stopOperations();
				throw new IllegalStateException("MealyMachine Control in unexpected state: " + getStatus());
				
			}
			
			shouldContinue = getStatus() != Status.STOPPED && composition.nextState();
			
		}
		
		stopOperations();
	}

	/**
	 * Starts the song playing
	 */
	public void start() {
		if(getStatus() == Status.PAUSED) {
			synchronized (this) {
				setStatus(Status.PLAYING);
				notify();
			}
		} else if(getStatus() == Status.STOPPED) {
			setStatus(Status.PLAYING);
			thread = new Thread(this);
			thread.start();
		}
		
	}
	
	/**
	 * Default actions for when the machine needs to stop its operations for one reason or another
	 */
	private void stopOperations() {
		setStatus(Status.STOPPED);
		composition.reset();
		Thread.currentThread().interrupt();
	}

	/**
	 * Gets the {@link stateMachine.StateMachine} composition
	 * 
	 * @return the {@link music.songOrchestrator.Control}'s composition
	 */
	public StateMachine<ArrayList<String>, String> getComposition() {
		return composition;
	}

	/**
	 * Sets the {@link stateMachine.StateMachine} composition 
	 * 
	 * @param composition the new composition
	 */
	public void setComposition(StateMachine<ArrayList<String>, String> composition) {
		this.composition = composition;
	}

	/**
	 * Gets the {@link music.songOrchestrator.Control}'s bpm
	 * 
	 * @return the {@link music.songOrchestrator.Control}'s bpm
	 */
	public int getBpm() {
		return bpm;
	}

	/**
	 * Sets the new {@link music.songOrchestrator.Control} bpm
	 * 
	 * @param bpm the new bpm
	 */
	public void setBpm(int bpm) {
		this.bpm = bpm;
		this.milisecsPerBeat = MILISECS_IN_MINUTE / bpm;
	}

	/**
	 * Gets the {@link Status} of the {@link music.songOrchestrator.Control}
	 * 
	 * @return the current {@link music.songOrchestrator.Control} {@link Status}
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Gets the default BPM of {@link music.songOrchestrator.Control}
	 * 
	 * @return default BPM of {@link music.songOrchestrator.Control}
	 */
	public static int getDefaultBPM() {
		return DEFAULT_BPM;
	}
	
	/**
	 * Sets the {@link music.songOrchestrator.Control} {@link Status}
	 * 
	 * @param status the new {@link Status}
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	// CALLABLE METHODS
	/**
	 * Changes the {@link music.Instrument} of the {@link music.MusicPlayer}
	 * 
	 * @param instrument the new {@link music.Instrument} to be changed to
	 */
	public void changeInstrumentTo(Instrument instrument) {
		player.setInstrument(instrument);
		shouldWait = false;
	}
	
	/**
	 * Changes the {@link music.Instrument} of the {@link music.MusicPlayer}
	 * 
	 * @param midiCode the midi code of the new {@link music.Instrument} to be changed to
	 */
	public void changeInstrumentTo(int midiCode) {
		player.setInstrument(midiCode);
		shouldWait = false;
	}
	
	/**
	 * Plays a {@link music.Note} through the {@link music.MusicPlayer}
	 * 
	 * @param note the {@link music.Note} to be played 
	 */
	public void playNote(Note note) {
		player.playNote(note);
		shouldWait = true;
	}
	
	/**
	 * Changes the {@link music.Instrument} of the {@link music.MusicPlayer} to its previous {@link music.Instrument}
	 */
	public void returnToPreviousInstrument() {
		player.setInstrument(player.getPreviousInstrument());
		shouldWait = false;
	}

	/**
	 * Waits a beat before continuing
	 */
	public void rest() {
		shouldWait = true;
	}	
	
	/**
	 * Changes the {@link music.Instrument} of the {@link music.MusicPlayer} to the next numerical {@link music.Instrument}
	 */
	public void nextInstrument() {
		player.setInstrument(player.getInstrument().getMidiCode() + 1);
		shouldWait = false;
	}
	
	/**
	 * Sets the BPM of {@link music.songOrchestrator.Control} to a random value
	 */
	public void randomBPM() {
		setBpm(RANDOM.nextInt(RANDOM_BPM_MIN, RANDOM_BPM_MAX + 1));
		shouldWait = false;
	}
	
	/**
	 * Plays a random {@link music.Note} through the {@link music.MusicPlayer}
	 */
	public void playRandomNote() {
		Note[] notes = Note.values();
		Note noteToBePlayed = notes[RANDOM.nextInt(notes.length)];
		playNote(noteToBePlayed);
		
		shouldWait = true;
	}
	
	/**
	 * Doubles the volume of the {@link music.MusicPlayer}
	 */
	public void doubleVolume() {
		player.setVolume(player.getVolume() * 2);	
		shouldWait = false;
	}
	
	/**
	 * Returns the volume of the {@link music.MusicPlayer} to default
	 */
	public void returnToDefaultVolume() {
		player.setVolume(MusicPlayer.getDefaultVolume() * 2);	
		shouldWait = false;
	}
	
	/**
	 * Raises the BPM of {@link music.songOrchestrator.Control} by a certain amount
	 * 
	 * @param amount the amount to raise the BPM by 
	 */
	public void raiseBPMBy(int amount) {
		setBpm(getBpm() + amount);
		shouldWait = false;
	}
	
	/**
	 * Raises the octave of {@link music.songOrchestrator.Control} by one 
	 */
	public void raiseOctave() {
		player.setOctave(player.getOctave() + 1);
		shouldWait = false;
	}
	
	/**
	 * Lowers the octave of {@link music.songOrchestrator.Control} by one 
	 */
	public void lowerOctave() {
		player.setOctave(player.getOctave() - 1);
		shouldWait = false;
	}		

}
