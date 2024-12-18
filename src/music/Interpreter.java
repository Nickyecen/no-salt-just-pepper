package music;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jfugue.pattern.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import utilitaries.CommandAddToString;
import utilitaries.CommandAddToList;
import utilitaries.InputAdapter;
import stateMachine.MealyTransition;
import stateMachine.State;
import stateMachine.StateMachine;
import music.MusicPlayer;

/* Runs the input string through {@link utilitaries.MealyMachine} described
 * in a .json file so it can be ready to be read by
 * (@link music.songOrchestrator.Orchestrator).
 */ 
public class Interpreter {
	
	private static final int RANDOM_BPM_MIN = 60;
	private static final int RANDOM_BPM_MAX = 120;
	private static final int RAISE_BPM_RATE = 80;
	
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
		
		// Build interpretation array list
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
	public Pattern interpretToJFuguePattern() {
		
		Pattern song = new Pattern();
		MusicPlayer.Builder musicPlayerBuilder = new MusicPlayer.Builder();
		MusicPlayer musicPlayer = musicPlayerBuilder.build();
		
		String newFragment = "";
		int currentBPM = 60;
		
		for(String symbol : this.interpretation) {
			switch(symbol){
				case "V": // Raise volume; this command doesn't reflect on the pattern;
					newFragment = "";
					break;
				case "v": // Lower volume; this command doesn't reflect on the pattern;
					newFragment = "";
					break;
				case "O": // Raise octave;
					musicPlayer.setOctave(musicPlayer.getOctave()+1);
					break;
				case "o": // Lower octave;
					musicPlayer.setOctave(musicPlayer.getOctave()-1);
					break;
				case "M": // Raise BPM by 80;
					currentBPM += RAISE_BPM_RATE;
					newFragment = "T" + String.valueOf(currentBPM) + " ";
					break;
				case "m": // Randomize BPM between 60 and 120;
					currentBPM = new Random().nextInt(RANDOM_BPM_MIN, RANDOM_BPM_MAX+1);
					newFragment = "T" + String.valueOf(currentBPM) + " ";
					break;
				case "T": // Change instrument to TELEPHONE_RING;
					musicPlayer.setInstrument(Instrument.TELEPHONE_RING);
					newFragment = "I" + String.valueOf(musicPlayer.getInstrument().ordinal()) + " ";
					break;
				case "I": // Increase current instrument;
					musicPlayer.setInstrument(musicPlayer.getInstrument().ordinal() + 1);
					newFragment = "I" + String.valueOf(musicPlayer.getInstrument().ordinal()) + " ";
					break;
				case "i": // Go back to previous instrument;
					musicPlayer.setInstrument(musicPlayer.getPreviousInstrument());
					newFragment = "I" + String.valueOf(musicPlayer.getInstrument().ordinal()) + " ";
					break;
				case "n": // Register random note, along with its octave;
					Note randomNote = Note.values()[new Random().nextInt(Note.values().length)];
					newFragment = randomNote.name() +  String.valueOf(musicPlayer.getOctave()) + " ";
					break;
				case "R": // Register pause;
					newFragment = symbol + " ";
					break;
				default: // All the other cases should also be notes;
					newFragment = symbol +  String.valueOf(musicPlayer.getOctave()) + " ";
					break;
			}
			song.add(newFragment);
		}
		
		return song;
	}
	
	public void interpretToMIDI(String word) {
		//TODO
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

