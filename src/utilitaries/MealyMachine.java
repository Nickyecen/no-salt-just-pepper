package utilitaries;

import java.util.HashMap;

/**
 * A Mealy state machine that will read through a state machine and execute outputs in its transitions
 * 
 * @param <K> the key to be used to hash the the next state 
 * 
 * @author nickyecen
 * @author 
 */
public class MealyMachine<K> {

	// TODO: Create method
	/**
	 * Constructs the Mealy state machine for execution
	 * 
	 * @param initialState the state in which the machine will start execution
	 * @param stateMap the state map that the machine will go through to execute
	 * 
	 * @author 
	 */
	public MealyMachine(String initialState, HashMap<String, HashMap<K, Tuple<String, Command>>> stateMap) {
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
}
