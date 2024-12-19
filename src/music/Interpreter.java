package music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import utilitaries.CommandAddToList;
import utilitaries.InputAdapter;
import stateMachine.MealyTransition;
import stateMachine.State;
import stateMachine.StateMachine;
import music.MusicPlayer;
import music.songOrchestrator.WriteControl;
import controlCommands.CommandMakeWriterDo;

/* Runs the input string through {@link utilitaries.MealyMachine} described
 * in a .json file so it can be ready to be read by
 * (@link music.songOrchestrator.Orchestrator).
 */ 
public class Interpreter {
	
	private static final int RANDOM_BPM_MIN = 60;
	private static final int RANDOM_BPM_MAX = 120;
	private static final int RAISE_BPM_RATE = 80;
	final private static String FILE_PATH = "src/res/patternMachine.json";
	
	private StateMachine<ArrayList<String>, String> decoderMachine;
	private ArrayList<String> interpretation = new ArrayList<String>();	
	
	private Interpreter(State<String> state) {
		this.decoderMachine = new StateMachine<ArrayList<String>, String>(state);
	}

	/* Creates the {@link utilitaries.MealyMachine} of the (@link music.Interpreter)
	 * 
	 * @param filePath name of the file which contains the 
	 * {@link utilitaries.MealyMachine}, having its'
	 * {@link stateMachine.State} and {@link stateMachine.Transitions}
	 */
	public static Interpreter loadFrom(String filePath) {
		
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
		
		Interpreter interpreter = new Interpreter(stateMap.get("q0"));
				
		for(Map.Entry<String, HashMap<String, HashMap<String, String>>> stateReference : data.entrySet()) {
			State<String> state = stateMap.get(stateReference.getKey());
			for(Map.Entry<String, HashMap<String, String>> symbolReference : stateReference.getValue().entrySet()) {
				String symbol = symbolReference.getKey();
				String nextStateName = symbolReference.getValue().get("nextState");
				String value = symbolReference.getValue().get("value");
				
				State<String> nextState = stateMap.get(nextStateName);
				CommandAddToList command = new CommandAddToList(interpreter.interpretation, value);
				state.addTransition(symbol, MealyTransition.to(nextState).doing(command));
				
			}
		}
		
		return interpreter;
	}
	
	/* Resets the {@link stateMachine.State}
	 * and clears the interpretation (output string)
	 */
	public void reset() {
		this.interpretation.clear();
		this.decoderMachine.reset();
	}
	
	/* Runs the {@link utilitaries.MealyMachine} using
	 * word as the parameter and saves the output in
	 * (@link music.Interpreter) interpretationString
	 * 
	 * @param word string that will be passed as input 
	 * parameter of (@link utilitaries.MealyMachine}
	 */
	public void interpretToDecoder(String word) {
		reset();

		InputAdapter inputAdapter = new InputAdapter(word);
		inputAdapter.run();
		word = inputAdapter.getOutput();
		
		ArrayList<String> parsedWord = new ArrayList<String>();
		
		char[] convertedWord = word.toCharArray();
		for(char symbol : convertedWord) {
			switch(symbol) {
				case '\n':
					parsedWord.add("NL");
					break;
				default:
					parsedWord.add(String.valueOf(symbol));
					break;
			}
		}
		
		this.decoderMachine.run(parsedWord);
		ArrayList<String> parsedInterpretation = new ArrayList<String>(interpretation);
		interpretation.clear();
		for(String value : parsedInterpretation) {
			for(char symbol : value.toCharArray()) {
				interpretation.add(String.valueOf(symbol));
			}	
		}
		
	}
	
	/* Converts the characters in the 
	 * (@link music.Interpreter) interpretation
	 * string to their equivalent JFUgue pattern
	 * 
	 * @return Pattern JFugue pattern of the song to be played
	 */
	private Pattern interpretToJFuguePattern(int inicialBPM) {
		
		Pattern song = new Pattern();
		WriteControl control = new WriteControl(inicialBPM);
		
		ObjectMapper mapper = new ObjectMapper();
		File fileObj = new File(FILE_PATH);
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
		
		StateMachine<ArrayList<String>, String> writer = new StateMachine<ArrayList<String>, String>(stateMap.get("q0"));
				
		for(Map.Entry<String, HashMap<String, HashMap<String, String>>> stateReference : data.entrySet()) {
			State<String> state = stateMap.get(stateReference.getKey());
			for(Map.Entry<String, HashMap<String, String>> symbolReference : stateReference.getValue().entrySet()) {
				String symbol = symbolReference.getKey();
				String nextStateName = symbolReference.getValue().get("nextState");
				String commandName = symbolReference.getValue().get("command");		
				State<String> nextState = stateMap.get(nextStateName);
		
				CommandWrite command;
				
				command = new CommandMakeWriterDo(control, commandName);
				
				state.addTransition(symbol, MealyTransition.to(nextState).doing(command));
			}
		}
		
		writer.run(this.interpretation);
		song.add(control.getPattern());
		
		return song;
		
	}
	
	 /* Writes the JFugue pattern from (@link music.Interpreter)
	  * interpretation to a midi file
	  * 
	  * @Param file the file file_path where it sill be saved
	  */
	public void interpretToMIDI(File filePath, int inicialBPM) {
		
		try (FileOutputStream fos = new FileOutputStream(filePath)){
            MidiFileManager.savePatternToMidi(interpretToJFuguePattern(inicialBPM), fos);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

	}
	
	 /* Returns the (@link music.Interpreter)
	  * interpretation string
	  * 
	  * @return the interpretation of (@link music.Interpreter)
	  */
	public ArrayList<String> getInterpretation() {
		return interpretation;
	}

}

