package music;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import utilitaries.CommandAddToList;
import stateMachine.MealyTransition;
import stateMachine.State;
import stateMachine.StateMachine;

public class Interpreter extends StateMachine<ArrayList<String>, String> {
	
	private ArrayList<String> interpretation = new ArrayList<String>();

	private Interpreter(State<String> state) {
		super(state);
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
				CommandAddToList command = new CommandAddToList(interpreter.interpretation, value);				
				
				state.addTransition(symbol, MealyTransition.to(nextState).doing(command));
			}
		}
		
		return interpreter;
	}
	
	@Override
	public void reset() {
		this.interpretation.clear();
		super.reset();
	}
	
	public void interpret(String word) {

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
		
		super.run(parsedWord);
	}
	
	public ArrayList<String> getInterpretation() {
		return interpretation;
	}

}
