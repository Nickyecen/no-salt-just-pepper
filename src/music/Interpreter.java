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

public class Interpreter {
	
	private static final int RANDOM_BPM_MIN = 60;
	private static final int RANDOM_BPM_MAX = 120;
	private static final int RAISE_BPM_RATE = 80;
	
	private StateMachine<ArrayList<String>, String> decoderMachine;
	private static StringBuilder interpretationString = new StringBuilder();
	private ArrayList<String> interpretation = new ArrayList<String>();	
	
	private Interpreter(State<String> state) {
		this.decoderMachine = new StateMachine<ArrayList<String>, String>(state);
	}

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
				CommandAddToString command = new CommandAddToString(interpretationString, value);
				state.addTransition(symbol, MealyTransition.to(nextState).doing(command));
				
			}
		}
		
		return interpreter;
	}
	
	public void reset() {
		this.interpretation.clear();
		this.decoderMachine.reset();
	}
	
	public void interpretToDecoder(String word) {

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
		CommandAddToList command = new CommandAddToList(this.interpretation, "");
		for (int i = 0; i < interpretationString.toString().length(); i++) {
			command.setValue(String.valueOf(interpretationString.charAt(i)));
			command.execute();
		}
	}
	
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
	
	public ArrayList<String> getInterpretation() {
		return interpretation;
	}

}
