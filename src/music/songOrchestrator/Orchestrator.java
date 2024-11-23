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
	 * 
	 * @author nickyecen
	 */
	public Orchestrator(MealyMachine composition) {
		this.control = new Control(composition);
	}

	/**
	 * Starts playing the composition
	 * 
	 * @author nickyecen
	 */
	void play() {
		control.setStatus(Status.PLAYING);
		if(!control.isRunning()) control.run();
	}
	
	/**
	 * Pauses the playing of the composition
	 * 
	 * @author nickyecen
	 */
	void pause() {
		control.setStatus(Status.PAUSED);
	}
	
	/**
	 * Stops the playing of the composition
	 * 
	 * @author nickyecen
	 */
	void stop() {
		control.setStatus(Status.STOPPED);
	}
	
	/**
	 * Gets the midi song being orchestrated
	 * 
	 * @return the midi song being orchestrated
	 * 
	 * @author nickyecen
	 */
	public String getMidiSong() {
		return midiSong;
	}

	/**
	 * Sets the midi song being orchestrated
	 * 
	 * @param midiSong the new midi song
	 * 
	 * @author nickyecen
	 */
	public void setMidiSong(String midiSong) {
		this.midiSong = midiSong;
		control.getComposition().setEntry(midiSong);
	}

	/**
	 * Gets the control being used
	 * 
	 * @return the control
	 * 
	 * @author nickyecen
	 */
	public Control getControl() {
		return control;
	}

}
