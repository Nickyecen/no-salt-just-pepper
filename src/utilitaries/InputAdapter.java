package utilitaries;

/*this class receives a string and when it is run() substitutes the
 * characters that fall into the else case are rewritten to 'X'
 */
public class InputAdapter {
	private String input;
	
	//Builder which receives the initial string
	public InputAdapter(String input) {
		this.input = input;
	}
	
	//Rewrites the input string so that all characters that would 
	//fall into the else case are rewritten as 'X'
	public String run() {
		int inputLength = input.length();
		StringBuilder adaptedInput = new StringBuilder(input);
		
		for(int i = 0; i < inputLength; i++) {
			char currentChar = Character.toUpperCase(input.charAt(i));
			switch(currentChar) {
				case 'A':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'B':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'C':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'D':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'E':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'F':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'G':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'I':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'O':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'U':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'R':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'P':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case 'M':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case '+':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case '-':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case '?':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case ';':
					adaptedInput.setCharAt(i, currentChar);
					break;
				case '\n':
					adaptedInput.setCharAt(i, currentChar);
					break;
				default:
					adaptedInput.setCharAt(i, 'X');
					break;
			}
		}
		return adaptedInput.toString();
	}

}
