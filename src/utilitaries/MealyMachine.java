package utilitaries;

import java.util.HashMap;

/**
 * A Mealy state machine that will read through a state machine and execute outputs in its transitions
 * 
 * @author nickyecen
 * @author 
 */
public class MealyMachine {
	
	private String entry;
	
	private int entryPosition;
	private String currentState;
	private String initialState;
	
	private final HashMap<String, HashMap<Character, Tuple<String, Command>>> stateMap;

	// TODO: Create method
	/**
	 * Constructs the Mealy state machine for execution
	 * 
	 * @param initialState the state in which the machine will start execution
	 * @param stateMap the state map that the machine will go through to execute
	 * 
	 * @author nickyecen
	 * @author
	 */
	public MealyMachine(String initialState, HashMap<String, HashMap<Character, Tuple<String, Command>>> stateMap) {
		this.initialState = initialState;
		this.stateMap = stateMap;
		// TODO
	}

	// TODO: Create method
	/**
	 * Runs the Mealy Machine using the stateMap with a given input
	 * 
	 * @param input the input to be read by the Machine
	 * 
	 * @author 
	 */
	public void run(String input) {
		// TODO
	}
	
	/**
	 * Runs the Mealy Machine using the stateMap
	 * 
	 * @author 
	 */
	public void run() {
		// TODO
	}
	
	/**
	 * Resets the MealyMachine to the start of the execution
	 * 
	 * @author nickyecen
	 */
	public void reset() {
		this.entryPosition = 0;
		this.currentState = initialState;
	}
	
	/**
	 * Goes to the next state in execution until it is finished executing
	 * 
	 * @return true if there is a nextState to be played
	 */
	public boolean nextState() {
		boolean isThereNextState = false;
		// TODO
		return isThereNextState;
	}

	/**
	 * Gets the entry being read
	 * 
	 * @return the entry being read
	 * 
	 * @author nickyecen
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * Sets the entry being read
	 * 
	 * @param entry the new entry to be read
	 * 
	 * @author nickyecen
	 */
	public void setEntry(String entry) {
		this.entry = entry;
		reset();
	}
}
