package music.songOrchestrator;

import java.util.ArrayList;

import stateMachine.StateMachine;

/**
 * Class that will directly control the {@link utilitaries.MealyMachine} responsible for
 * playing the song
 * 
 * @author nickyecen
 */
public class Control implements Runnable {
	
	final private int MILISECS_IN_MINUTE = 60000;

	private StateMachine<ArrayList<String>, String> composition;
	private int bpm = 60;
	private int milisecsPerBeat = MILISECS_IN_MINUTE / bpm;
	private Status status = Status.STOPPED;
	
	private Thread thread;
	
	/**
	 * Constructs the Control with the composition it will play
	 * 
	 * @param composition
	 */
	public Control(StateMachine<ArrayList<String>, String> composition) {
		this.composition = composition;
	}
	
	/**
	 * Runs the {@link utilitaries.MealyMachine} to play the song
	 */
	public void run() {
		setStatus(Status.PLAYING);

		boolean shouldContinue = composition.nextState() && getStatus() != Status.STOPPED;
		while(shouldContinue) {
			
			try {
				Thread.sleep(milisecsPerBeat);
			} catch (InterruptedException e) {
				break;
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
	 * Gets the {@link utilitaries.MealyMachine} composition
	 * 
	 * @return the Control's composition
	 */
	public StateMachine<ArrayList<String>, String> getComposition() {
		return composition;
	}

	/**
	 * Sets the {@link utilitaries.MealyMachine} composition 
	 * 
	 * @param composition the new composition
	 */
	public void setComposition(StateMachine<ArrayList<String>, String> composition) {
		this.composition = composition;
	}

	/**
	 * Gets the Control's bpm
	 * 
	 * @return the Control's bpm
	 */
	public int getBpm() {
		return bpm;
	}

	/**
	 * Sets the new Control bpm
	 * 
	 * @param bpm the new bpm
	 */
	public void setBpm(int bpm) {
		this.bpm = bpm;
		this.milisecsPerBeat = MILISECS_IN_MINUTE / bpm;
	}

	/**
	 * Gets the {@link Status} of the Control
	 * 
	 * @return the current Control status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the Control {@link Status}
	 * 
	 * @param status the new {@link Status}
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}
