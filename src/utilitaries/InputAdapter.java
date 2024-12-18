package utilitaries;

import java.util.ArrayList;
import java.util.List;

/* This class was supposed to receive a string and an alphabet filepath;
 * I could not resolve a method to get the alphabet from an external font, 
 * like a .json file. I turned it into a constant for temporary usage.
 * 
 * When run() is called, it substitutes all the characters
 * that are not included in the alphabet with 'X';
 */

public class InputAdapter {
	
	private static final List<Character> ALPHABET = List.of(
	        'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g',
	        ' ', '\n', '+', '-', 'I', 'i', 'O', 'o', 'U', 'u', 'R', '?', 'P', 'M', ';'
	    ); 
	
	private String input;
	private String output;
	private ArrayList<Character> alphabet;
	
	// Builder which receives the initial string and alphabet filePath
	public InputAdapter(String input) {
		this.input = input;
		this.alphabet = new ArrayList<>(ALPHABET);
	}
	
	// Rewrites the input string so that all characters that are 
	// not included in the alphabet are rewritten as 'X'
	public void run() {
		StringBuilder adaptedInput = new StringBuilder();
		
		for (char c : input.toCharArray()) {
            if (this.alphabet.contains(c)) {
            	adaptedInput.append(c);
            } else {
            	adaptedInput.append('X');
            }
        }

		this.output = adaptedInput.toString();
	}

	public String getOutput() {
		return this.output;
	}
}
