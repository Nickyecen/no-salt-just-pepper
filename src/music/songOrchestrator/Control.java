package music.songOrchestrator;

import utilitaries.MealyMachine;

/**
 * Class that will directly control the {@link utilitaries.MealyMachine} responsible for
 * playing the song
 */
public class Control {

	private MealyMachine composition;
	private int bpm = 60;
	private Status status = Status.STOPPED;
	private boolean running = false;
	
	private Thread track;
	
	/**
	 * Constructs the Control with the composition it will play
	 * 
	 * @param composition
	 * 
	 * @author nickyecen
	 */
	public Control(MealyMachine composition) {
		this.composition = composition;
		track = new Thread();
	}
	
	/**
	 * Runs the {@link utilitaries.MealyMachine} to play the song
	 * 
	 * @author nickyecen
	 */
	public void run() {
		// TODO
	}

	/**
	 * Gets the {@link utilitaries.MealyMachine} composition
	 * 
	 * @return the Control's composition
	 * 
	 * @author nickyecen
	 */
	public MealyMachine getComposition() {
		return composition;
	}

	/**
	 * Sets the {@link utilitaries.MealyMachine} composition 
	 * 
	 * @param composition the new composition
	 * 
	 * @author nickyecen
	 */
	public void setComposition(MealyMachine composition) {
		this.composition = composition;
	}

	/**
	 * Gets the Control's bpm
	 * 
	 * @return the Control's bpm
	 * 
	 * @author nickyecen
	 */
	public int getBpm() {
		return bpm;
	}

	/**
	 * Sets the new Control bpm
	 * 
	 * @param bpm the new bpm
	 * 
	 * @author nickyecen
	 */
	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	/**
	 * Gets the {@link Status} of the Control
	 * 
	 * @return the current Control status
	 * 
	 * @author nickyecen
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the Control {@link Status}
	 * 
	 * @param status the new {@link Status}
	 * 
	 * @author nickyecen
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Checks if the {@link utilitaries.MealyMachine} is still running
	 * 
	 * @return true if the {@link utilitaries.MealyMachine} is still running
	 * 
	 * @author nickyecen
	 */
	public boolean isRunning() {
		return running;
	}
	
}
