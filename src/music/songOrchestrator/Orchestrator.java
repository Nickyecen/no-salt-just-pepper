package music.songOrchestrator;

import utilitaries.MealyMachine;

/**
 * Class that orchestrates the {@link utilitaries.MealyMachine} that will play the midi
 * 
 * @author nickyecen
 */
public class Orchestrator {
	
	private String midiSong;
	private final Control control;

	/**
	 * Constructs the Orchestrator with the composition it will guide
	 * 
	 * @param composition
	 */
	public Orchestrator(MealyMachine composition) {
		this.control = new Control(composition);
	}

	/**
	 * Starts playing the composition
	 */
	public void play() {
		if(control.getStatus() == Status.STOPPED) {
			control.getComposition().setEntry(midiSong);
		}
		control.start();
	}
	
	/**
	 * Pauses the playing of the composition
	 */
	public void pause() {
		if(control.getStatus() != Status.STOPPED) control.setStatus(Status.PAUSED);
	}
	
	/**
	 * Stops the playing of the composition
	 */
	public void stop() {
		control.setStatus(Status.STOPPED);
	}
	
	/**
	 * Gets the midi song being orchestrated
	 * 
	 * @return the midi song being orchestrated
	 */
	public String getMidiSong() {
		return midiSong;
	}

	/**
	 * Sets the midi song being orchestrated
	 * 
	 * @param midiSong the new midi song
	 */
	public void setMidiSong(String midiSong) {
		this.midiSong = midiSong;
		control.getComposition().setEntry(midiSong);
	}

	/**
	 * Gets the control being used
	 * 
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

}
