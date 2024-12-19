package utilitaries;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class receives a string and alphabet and when run() 
 * is called, it substitutes all the characters
 * that are not included in the alphabet with 'X';
 */
public class InputAdapter {
	
	
	private String input;
	private String output;
	private Set<String> alphabet; 
	
	/**
	 * Builder which receives the initial string and alphabet filePath
	 * 
	 * @param input string for {@link utilitaries.InputAdapter} {@link input}
	 */
	public InputAdapter(String input) {
		this.input = input;
		this.alphabet = loadAlphabet();
	}
	
	/**
	 * Rewrites the {@link input} string so that all characters that are 
	 * not included in the alphabet are rewritten as 'X' in the {@link output}
	 */
	public void run() {
		StringBuilder adaptedInput = new StringBuilder();
		
		for (char c : input.toCharArray()) {
            if (this.alphabet.contains(String.valueOf(c))) {
            	adaptedInput.append(c);
            } else {
            	adaptedInput.append('X');
            }
        }

		this.output = adaptedInput.toString();
	}
	
	/**
	 * Loads the {@link alphabet} from a json file
	 */
	public Set<String> loadAlphabet() {
		Set<String> alphabet = new HashSet<String>();
		
		ObjectMapper mapper = new ObjectMapper();
		File fileObj = new File("src/res/decoderMachine.json");
		HashMap<String, HashMap<String, HashMap<String, String>>> data = null;
		try {
			
			data = mapper.readValue(fileObj, new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {});
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	
		for(Map.Entry<String, HashMap<String, HashMap<String, String>>> entry : data.entrySet()) {
			for(Map.Entry<String, HashMap<String, String>> symbolEntry : entry.getValue().entrySet()) {
				alphabet.add(symbolEntry.getKey());
			}
		}
		
		return alphabet;
		
	}

  /**
	 * Gets the {@link output} of the {@link utilitaries.InputAdapter}
	 *
	 * @return the current {@link utilitaries.InputAdapter} {@link output}
	 */
	public String getOutput() {
		return this.output;
	}
}
