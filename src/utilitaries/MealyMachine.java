package utilitaries;

import java.util.HashMap;

/**
 * A Mealy state machine that will read through a state machine and execute outputs in its transitions
 * 
 * @author 
 * @author nickyecen
 */
public class MealyMachine {
	
	private String entry;
	private int entryLength;
	
	private int entryPosition = 0;
	private String currentState;
	private String initialState;
	
	private final HashMap<String, HashMap<Character, Tuple<String, Command>>> stateMap;

	// TODO: Create method
	/**
	 * Constructs the Mealy state machine for execution
	 * 
	 * @param initialState the state in which the machine will start execution
	 * @param stateMap the state map that the machine will go through to execute
	 */
	public MealyMachine(String initialState, HashMap<String, HashMap<Character, Tuple<String, Command>>> stateMap) {
		this.initialState = initialState;
		this.currentState = initialState;
		this.stateMap = stateMap;
		// TODO
	}

	// TODO: Create method
	/**
	 * Runs the Mealy Machine using the stateMap with a given input
	 * 
	 * @param input the input to be read by the Machine
	 */
	public void run(String input) {
		setEntry(input);
		while(nextState()) {};
		// TODO
	}
	
	/**
	 * Runs the Mealy Machine using the stateMap
	 */
	public void run() {
		while(nextState()) {};
		// TODO
	}
	
	/**
	 * Resets the MealyMachine to the start of the execution
	 */
	public void reset() {
		this.entryPosition = 0;
		this.currentState = initialState;
	}
	
	/**
	 * Goes to the next state in execution until it is finished executing
	 */
	public boolean nextState() {
		stateMap.get(currentState).get(entry.charAt(entryPosition)).getSecond().execute();
		currentState = stateMap.get(currentState).get(entry.charAt(entryPosition)).getFirst();
		entryPosition++;
		boolean isThereNextState = entryPosition < entryLength;
		//System.out.println("Beat number: " + entryPosition);
		// TODO
		return isThereNextState;
	}

	/**
	 * Gets the entry being read
	 * 
	 * @return the entry being read
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * Sets the entry being read
	 * 
	 * @param entry the new entry to be read
	 */
	public void setEntry(String entry) {
		this.entry = entry;
		this.entryLength = entry.length();
		reset();
	}
}
